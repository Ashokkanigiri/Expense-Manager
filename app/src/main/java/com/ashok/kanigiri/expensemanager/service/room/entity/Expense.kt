package com.ashok.kanigiri.expensemanager.service.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int = 0,
    val expenseMonthId: Int,
    val expenseName: String,
    val expenseDate: String,
    val expenseCategoryId: Int,
    val createdDate: String,
    val expensePrice: Double
)

data class ExpenseGraphModel(@ColumnInfo(name = "SUM(expensePrice)") val expensePrice: Double,@ColumnInfo(name = "expenseCategoryId") val expenseCategoryId: Int)
