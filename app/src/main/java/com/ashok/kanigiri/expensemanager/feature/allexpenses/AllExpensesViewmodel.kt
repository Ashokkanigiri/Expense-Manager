package com.ashok.kanigiri.expensemanager.feature.allexpenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class AllExpensesViewmodel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {

    val expandableAdapter = AllExpensesExpandableAdapter(this)
    val getTotalExpenses = roomRepository.getExpenseDao().getTotalExpenses()


    fun deleteExpense(expenseId: Int){
        roomRepository.getExpenseDao().deleteExpense(expenseId)
        expandableAdapter.notifyDataSetInvalidated()
    }

    fun loadExpandableData(){
        val data  = ArrayList<Map<Int, List<Expense>>>()
        val keys = ArrayList<ExpenseMonth>()
        val expenseMonthsList = roomRepository.getExpenseMonthDao().getAllExpenseMonthsData()
        expenseMonthsList.forEach {
            val expense = roomRepository.getExpenseDao().getAllExpensesByExpenseMonthId(it.expenseMonthId)
            data.add(mapOf(Pair(it.expenseMonthId, expense)))
            keys.add(it)
        }
        expandableAdapter.setUpAdapter(data, keys)
    }

}