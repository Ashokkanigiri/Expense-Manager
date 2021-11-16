package com.ashok.kanigiri.expensemanager.feature.allexpenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllExpensesViewmodel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {

    val adapter = AllExpensesListAdapter(this)

    fun getAllExpenses(): LiveData<List<Expense>>{
        return roomRepository.getExpenseDao().getAllExpenses()
    }

}