package com.ashok.kanigiri.expensemanager.service

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseCategoryDao
import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseDao
import com.ashok.kanigiri.expensemanager.service.room.dao.ExpenseMonthDao
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth
import com.ashok.kanigiri.expensemanager.service.room.typeconverters.ExpenseTypeConverter

@Database(entities = [ExpenseCategory::class, Expense::class, ExpenseMonth::class], exportSchema = false, version = 1)
@TypeConverters(ExpenseTypeConverter::class)
abstract class ExpenseAppDB: RoomDatabase() {
    abstract fun expenseCateforyDao(): ExpenseCategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseMonthDao(): ExpenseMonthDao
}