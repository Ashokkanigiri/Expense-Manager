package com.ashok.kanigiri.expensemanager.service.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey
    val expenseId: String,
    val expenseName: String,
    val expenseCategoryId: String,
    val createdDate: String,
    val expensePrice: Double
)
