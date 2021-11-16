package com.ashok.kanigiri.expensemanager.feature.allexpenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AllExpensesViewmodel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {

    val adapter = AllExpensesListAdapter(this)
    val getTotalExpenses = roomRepository.getExpenseDao().getTotalExpenses()
    val event = SingleLiveEvent<AllExpensesViewmodelEvent>()
    var fromDate:String? = roomRepository.getExpenseDao().getminimumCreatedDateExpense()?.createdDate
    var toDate: String? = roomRepository.getExpenseDao().getMaximumCreatedDateExpense()?.createdDate

    fun getAllExpenses(): LiveData<List<Expense>>{
        return roomRepository.getExpenseDao().getAllExpenses()
    }

    fun getAllExpensesForFilteredDate(from: String, to: String){
        event.postValue(AllExpensesViewmodelEvent.LoadExpenses( roomRepository.getExpenseDao().getTotalExpensesForGivenDate(from, to)))
    }

    fun filterExpensesByDate(){
        event.postValue(AllExpensesViewmodelEvent.ShowCalenderDialog)
    }
    fun filterExpensesByCategorys(){

    }
    fun sortExpensesHightoLow(){
        event.postValue(AllExpensesViewmodelEvent.LoadExpenses(roomRepository.getExpenseDao().getTotalExpensesInDescendingOrder(fromDate, toDate)))
    }
    fun sortExpensesLowToHigh(){
        event.postValue(AllExpensesViewmodelEvent.LoadExpenses(roomRepository.getExpenseDao().getTotalExpensesInAscendingOrder(fromDate, toDate)))
    }
    fun sortRecentExpenses(){
        event.postValue(AllExpensesViewmodelEvent.LoadExpenses(roomRepository.getExpenseDao().getTotalRecentExpenses(fromDate, toDate)))
    }

     fun setDefaultDates(){
         fromDate= roomRepository.getExpenseDao().getminimumCreatedDateExpense()?.createdDate
         toDate= roomRepository.getExpenseDao().getMaximumCreatedDateExpense()?.createdDate
    }
}

sealed class AllExpensesViewmodelEvent(){
    object ShowCalenderDialog: AllExpensesViewmodelEvent()
    data class LoadExpenses(val data: List<Expense>?): AllExpensesViewmodelEvent()
    data class SortExpensesHighToLow(val data: List<Expense>): AllExpensesViewmodelEvent()
    data class SortExpensesLowToHigh(val data: List<Expense>): AllExpensesViewmodelEvent()
    data class SortRecentExpenses(val data: List<Expense>): AllExpensesViewmodelEvent()
}