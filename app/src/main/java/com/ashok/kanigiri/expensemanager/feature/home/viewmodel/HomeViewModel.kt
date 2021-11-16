package com.ashok.kanigiri.expensemanager.feature.home.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
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
    //2 Way binding
    var etSalary = ObservableField<String>()
    var event = SingleLiveEvent<HomeViewModelEvent>()
    var fromDate:String? = "1-${Calendar.MONTH}-${Calendar.YEAR})}"
    var toDate: String? = "${Calendar.getInstance().getActualMaximum(Calendar.MONTH)}-${Calendar.MONTH}-${Calendar.YEAR})"

    fun getTotalExpensesForGivenDate(from: String, to: String): LiveData<List<Expense>>{
        return roomRepository.getExpenseDao().getTotalExpensesForGivenDate(from, to)
    }

    fun logout(){
        event.postValue(HomeViewModelEvent.Logout)
    }

    fun handleFilterButton(){
        event.postValue(HomeViewModelEvent.HandleFilterButton)
    }


}

sealed class HomeViewModelEvent{
    object IsSalaryUpdated: HomeViewModelEvent()
    object Logout: HomeViewModelEvent()
    object HandleFilterButton: HomeViewModelEvent()
}
