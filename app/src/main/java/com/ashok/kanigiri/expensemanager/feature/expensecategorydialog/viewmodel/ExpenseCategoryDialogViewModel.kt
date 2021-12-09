package com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.ashok.kanigiri.expensemanager.utils.Event
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseCategoryDialogViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    val event = SingleLiveEvent<ExpenseCategoryDialogViewmodelEvent>()
    val expenseTargetPrice = ObservableField<String>()
    val expenseCategoryName = ObservableField<String>()
    var shouldShowExpensePrice = false
    val getTotalAllocatedPrice: Double = runBlocking(Dispatchers.IO) {
        (roomRepository.getCategoryDao().getTotalAllotedCategoryPrice(getLatestExpenseMonth()?.expenseMonthId?:1)) ?: 0.0
    }

    fun populateReserveCash() {
        event.postValue(ExpenseCategoryDialogViewmodelEvent.PopulateReserveCAsh(getReserveCash()))
    }

    private fun getReserveCash(): Double {
        return ((getLatestExpenseMonth()?.salary ?: 0.0) - getTotalAllocatedPrice)
    }

    fun getLatestExpenseMonth(): ExpenseMonth? {
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
    }


    fun insertExpenseCategory() {
        if (!shouldShowExpensePrice) {
            //
                event.postValue(ExpenseCategoryDialogViewmodelEvent.SendCreatedCAtegoryNAme(expenseCategoryName.get()))
        } else {
            if (expenseTargetPrice.get()?.trim() != "" && expenseTargetPrice.get()
                    ?.trim() != null
            ) {
                val totalPriceGivenForAllCategorys =
                    getTotalAllocatedPrice
                if ((totalPriceGivenForAllCategorys + (expenseTargetPrice.get()?.toDouble()
                        ?: 0.0)) <= SharedPreferenceService.getUserLoginModel(context)?.salary?.toDouble() ?: 0.0
                ) {
                    val getLatestMonth = roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
                    val expenseCategory = ExpenseCategory(
                        expenseCategoryTargetPrice = expenseTargetPrice.get()?.toDouble() ?: 0.0,
                        totalUtilizedPrice = 0.0,
                        expenseCategoryName = expenseCategoryName.get() ?: "",
                        createdDate = System.currentTimeMillis().toString(),
                        expenseMonthId = getLatestMonth?.expenseMonthId ?: 1,
                        isSelected = true
                    )

                    viewModelScope.launch(Dispatchers.IO) {
                        roomRepository.getCategoryDao().insert(expenseCategory)
                    }
                    event.postValue(ExpenseCategoryDialogViewmodelEvent.DismissDialog)
                } else {
                    val diff =
                        SharedPreferenceService.getUserLoginModel(context)?.salary?.toDouble()
                            ?: 0.0 - (totalPriceGivenForAllCategorys)
                    if (diff == 0.0) {
                        Toast.makeText(context, "MONTHLY SALARY EXHAUSTED, ", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            "MAXIMUM LIMIT REACHED, you can add upto maximum of $diff",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    fun onExpenseTargetPriceChanged(char: CharSequence) {
        expenseTargetPrice.set(char.toString())
    }

}

sealed class ExpenseCategoryDialogViewmodelEvent {
    data class PopulateReserveCAsh(val reserveCash: Double) : ExpenseCategoryDialogViewmodelEvent()
    data class SendCreatedCAtegoryNAme(val name: String?): ExpenseCategoryDialogViewmodelEvent()
    object DismissDialog: ExpenseCategoryDialogViewmodelEvent()
}