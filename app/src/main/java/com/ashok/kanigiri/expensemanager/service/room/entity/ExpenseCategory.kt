package com.ashok.kanigiri.expensemanager.service.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["expenseCategoryName"])
data class ExpenseCategory(
    val expenseCategoryId: String,
    val expenseCategoryTargetPrice: Double,
    val totalUtilizedPrice: Double,
    val expenseCategoryName: String,
    val createdDate: String
)