package com.ashok.kanigiri.expensemanager.feature.expenseList.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.feature.expenseList.ExpenseListViewModelEvent
import com.ashok.kanigiri.expensemanager.feature.expenseList.view.ExpenseListAdapter
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {

    val event : SingleLiveEvent<ExpenseListViewModelEvent> = SingleLiveEvent<ExpenseListViewModelEvent>()
    val adapter = ExpenseListAdapter(this)
    var expenseCategoryId: Int? = null
    var categoryName: String? = null

    fun getAllExpenses(expenseCategoryId: Int): LiveData<List<Expense>>{
        return roomRepository.getExpenseDao().getAllExpensesForACategoryForExpenseMonth(expenseCategoryId, getCurrentExpenseMonth()?.expenseMonthId?:1).asLiveData(Dispatchers.Main)
    }

    fun setAdapter(): ExpenseListAdapter{
        return adapter
    }

    fun submitList(data: List<Expense>){
        adapter.submitList(data)
    }

    fun deleteExpense(expense: Expense){
        viewModelScope.launch (Dispatchers.IO){
            roomRepository.getExpenseDao().deleteExpense(expense.expenseId)
            roomRepository.getCategoryDao().updateUtilizedPriceForCategory(expenseCategoryId?:0, -(expense.expensePrice))
        }
    }

    fun getCurrentExpenseMonth(): ExpenseMonth?{
        return roomRepository.getExpenseMonthDao().getLatestExpenseMonth()
    }
}