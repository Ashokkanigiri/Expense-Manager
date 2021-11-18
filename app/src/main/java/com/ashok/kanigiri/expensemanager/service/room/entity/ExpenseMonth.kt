package com.ashok.kanigiri.expensemanager.service.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseMonth(
    @PrimaryKey(autoGenerate = true)
    val expenseMonthId: Int = 0,
    val createdDate: String,
    val expenseMonth: Int,
    val salary: Double,
    val fromDate: String,
    val toDate: String,
    val totalUtilizedPrice: Double
)