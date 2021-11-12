package com.ashok.kanigiri.expensemanager.feature.home.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor( val roomRepository: RoomRepository, @ApplicationContext private val context: Context): ViewModel() {

    val getTotalExpenses = roomRepository.getExpenseDao().getTotalExpenses()
    val getAllExpenseCategorys = roomRepository.getCategoryDao().getSelectedCategorys()

    //2 Way binding
    var etSalary = ObservableField<String>()
    var event = SingleLiveEvent<HomeViewModelEvent>()

    fun updateSalary(){
        SharedPreferenceService.putUserSalary(context, etSalary.get()?.toInt()?:0)
        event.postValue(HomeViewModelEvent.IsSalaryUpdated)
    }

}

sealed class HomeViewModelEvent{
    object IsSalaryUpdated: HomeViewModelEvent()
}
