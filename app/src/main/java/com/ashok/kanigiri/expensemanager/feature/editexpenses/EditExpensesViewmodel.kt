package com.ashok.kanigiri.expensemanager.feature.editexpenses

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.widget.SeekBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.feature.choosecategory.ChooseCategoryViewmodelEvent
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.*

@HiltViewModel
class EditExpensesViewmodel @Inject constructor(
    private val roomRepository: RoomRepository,
    @ApplicationContext val context: Context
) :
    ViewModel() {
    val adapter = EditExpensesAdapter(this)
    var progressMap = mutableMapOf<String, Int>()
    var totalExpenses = ObservableField<Int>()
    val event = SingleLiveEvent<EditExpensesViewModelEvent>()
    var salary: Int = SharedPreferenceService.getUserLoginModel(context)?.salary?.toInt()?:0

    init {
        SharedPreferenceService.putBoolean(
            SharedPreferenceService.IS_USER_EDITED_CATEGORYS,
            false,
            context
        )
    }

    fun setAdapter(): EditExpensesAdapter {
        return adapter
    }

    fun getExpenseList(): LiveData<List<ExpenseCategory>> {
        return roomRepository.getCategoryDao().getSelectedCategorys()
    }

    fun createAccount() {
        if (totalExpenses.get() ?: 0 >= salary) {
            event.postValue(EditExpensesViewModelEvent.ShowSalaryLimitReachedSnackbar)
        } else {
            event.postValue(EditExpensesViewModelEvent.NavigateToMainActivity)
            createExpenseMonth()
        }
    }

    private fun createExpenseMonth() {
        val expenseMonth = ExpenseMonth(
            createdDate = (Timestamp(System.currentTimeMillis().toLong())).toString(),
            expenseMonth = AppUtils.getCurrentMonthInInt(),
            salary = SharedPreferenceService.getUserLoginModel(context)?.salary?.toDouble() ?: 0.0,
            fromDate = AppUtils.getFirstDayOnMonthInDateFormat(),
            toDate = AppUtils.getLastDayOfMonthInDateFormat(),
            totalUtilizedPrice = 0.0
        )
        roomRepository.getExpenseMonthDao().insertExpenseMonth(expenseMonth)
    }

    fun calculateTotalExpenses() {
        totalExpenses.set(progressMap.values.sum())
    }

    fun updateTargetPriceForCategory(categoryId: Int, targetPrice: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getCategoryDao().updateTargetPriceForCategory(categoryId, targetPrice)
        }
    }

    fun getTargetPriceForCategory(categoryId: Int): Double {
        return roomRepository.getCategoryDao().getTotalExpensePriceForCategory(categoryId)
    }

    fun cancelButtonClicked() {
        event.postValue(EditExpensesViewModelEvent.HandleCancelButtonClicked)
    }

}