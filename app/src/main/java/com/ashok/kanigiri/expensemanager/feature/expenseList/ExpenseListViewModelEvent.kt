package com.ashok.kanigiri.expensemanager.feature.expenseList

import com.ashok.kanigiri.expensemanager.service.room.entity.Expense

sealed class ExpenseListViewModelEvent{
    data class LoadAllExpensesForCategory(val expenses: List<Expense>): ExpenseListViewModelEvent()
}
