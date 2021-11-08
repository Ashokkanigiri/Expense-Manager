package com.ashok.kanigiri.expensemanager.service.room.repository

import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseCategoryDao
import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseDao
import javax.inject.Inject

class RoomRepository @Inject constructor(private val expenseCategoryDao: ExpenseCategoryDao, private val expenseDao: ExpenseDao) {
    fun getCategoryDao() = expenseCategoryDao
    fun getExpenseDao() = expenseDao
}