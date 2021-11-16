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

    val event = SingleLiveEvent<AllExpensesViewmodelEvent>()
    var fromDate:String? = "1-${Calendar.MONTH}-${Calendar.YEAR})}"
    var toDate: String? = "${Calendar.getInstance().getActualMaximum(Calendar.MONTH)}-${Calendar.MONTH}-${Calendar.YEAR})"

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

    }
    fun sortExpensesLowToHigh(){

    }
    fun sortRecentExpenses(){

    }
}

sealed class AllExpensesViewmodelEvent(){
    object ShowCalenderDialog: AllExpensesViewmodelEvent()
    data class LoadExpenses(val data: List<Expense>): AllExpensesViewmodelEvent()
}