package com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseCategoryDialogViewModel @Inject constructor(private val roomRepository: RoomRepository, @ApplicationContext private val context: Context) :
    ViewModel() {

    val expenseTargetPrice = ObservableField<String>()
    val expenseCategoryName = ObservableField<String>()
    val event = SingleLiveEvent<Event<Boolean>>()
    val sendCreatedExpenseNameEvent = SingleLiveEvent<String>()
    var shouldShowExpensePrice = false

    fun reserveCash() = SharedPreferenceService.getUserSalary(context) - (roomRepository.getCategoryDao().getTotalAllotedCategoryPrice())

    fun insertExpenseCategory() {
        if(!shouldShowExpensePrice){
            //
            sendCreatedExpenseNameEvent.postValue(expenseCategoryName.get())
        }else{
            if (expenseTargetPrice.get()?.trim() != "" && expenseTargetPrice.get()?.trim() != null) {
                val totalPriceGivenForAllCategorys = roomRepository.getCategoryDao().getTotalAllotedCategoryPrice()
                if((totalPriceGivenForAllCategorys+(expenseTargetPrice.get()?.toDouble()?:0.0))<= SharedPreferenceService.getUserSalary(context)){
                    val expenseCategory = ExpenseCategory(
                        expenseCategoryId = System.currentTimeMillis().toInt(),
                        expenseCategoryTargetPrice = expenseTargetPrice.get()?.toDouble()?:0.0,
                        totalUtilizedPrice = 0.0,
                        expenseCategoryName = expenseCategoryName.get()?:"",
                        createdDate = System.currentTimeMillis().toString(),
                        isSelected = true
                    )

                    GlobalScope.launch(Dispatchers.IO) {
                        roomRepository.getCategoryDao().insert(expenseCategory)
                    }
                    event.postValue(Event(true))
                }else{
                    val diff =  SharedPreferenceService.getUserSalary(context) - (totalPriceGivenForAllCategorys)
                    if(diff == 0.0){
                        Toast.makeText(context, "MONTHLY SALARY EXHAUSTED, ", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "MAXIMUM LIMIT REACHED, you can add upto maximum of $diff", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    fun onExpenseTargetPriceChanged(char: CharSequence) {
        expenseTargetPrice.set(char.toString())
    }

}