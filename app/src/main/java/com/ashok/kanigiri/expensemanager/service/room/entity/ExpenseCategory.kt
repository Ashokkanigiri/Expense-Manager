package com.ashok.kanigiri.expensemanager.service.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["expenseType"])
data class ExpenseCategory(
    val expenseCategoryId: String,
    val expenseCategoryTargetPrice: Double,
    val totalUtilizedPrice: Double,
    val expenseType: ExpenseTypes,
    val createdDate: String
)