package com.ashok.kanigiri.expensemanager.feature.updateSalary

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateSalaryDialogViewmodel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {
    val event = SingleLiveEvent<UpdateSalaryDialogViewmodelEvent>()
    val showErrorText = ObservableField<Boolean>()

    init {
        showErrorText.set(false)
    }

    fun onUpdateSalaryClicked() {
        event.postValue(UpdateSalaryDialogViewmodelEvent.UpdateSalary)
    }

    fun updateCurrentSalary(newSalary: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            if (newSalary > getCurrentAllocatedExpensesForMonth() ?: 0.0) {
                roomRepository.getExpenseMonthDao().updateSalaryForExpenseMonth(
                    newSalary,
                    getCurrentExpenseMonth()?.expenseMonthId ?: 1
                )
                dismissDialog()
            } else {
                showErrorText.set(true)
            }
        }
    }

    suspend fun getCurrentAllocatedExpensesForMonth(): Double? {
        return roomRepository.getCategoryDao()
            .getTotalAllotedCategoryPrice(getCurrentExpenseMonth()?.expenseMonthId ?: 1)
    }

    fun getCurrentExpenseMonth(): ExpenseMonth? {
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
    }

    fun dismissDialog() {
        event.postValue(UpdateSalaryDialogViewmodelEvent.DismissDialog)
    }

}

sealed class UpdateSalaryDialogViewmodelEvent() {
    object UpdateSalary : UpdateSalaryDialogViewmodelEvent()
    object DismissDialog : UpdateSalaryDialogViewmodelEvent()
}
