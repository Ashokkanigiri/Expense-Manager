package com.ashok.kanigiri.expensemanager.feature.updateSalary

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateSalaryDialogViewmodel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {
    val event = SingleLiveEvent<UpdateSalaryDialogViewmodelEvent>()
    val showErrorText = ObservableField<Boolean>()

    init {
        showErrorText.set(false)
    }

    fun onUpdateSalaryClicked(){
        event.postValue(UpdateSalaryDialogViewmodelEvent.UpdateSalary)
    }

    fun updateCurrentSalary(newSalary: Double){
        if(newSalary > getCurrentExpenseMonth()?.salary?:0.0){
            viewModelScope.launch {
                roomRepository.getExpenseMonthDao().updateSalaryForExpenseMonth(newSalary, getCurrentExpenseMonth()?.expenseMonthId?:1)
            }
            dismissDialog()
        }else{
            showErrorText.set(true)
        }
    }

    fun getCurrentExpenseMonth(): ExpenseMonth?{
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
    }

    fun dismissDialog(){
        event.postValue(UpdateSalaryDialogViewmodelEvent.DismissDialog)
    }

}

sealed class UpdateSalaryDialogViewmodelEvent(){
    object UpdateSalary: UpdateSalaryDialogViewmodelEvent()
    object DismissDialog: UpdateSalaryDialogViewmodelEvent()
}
