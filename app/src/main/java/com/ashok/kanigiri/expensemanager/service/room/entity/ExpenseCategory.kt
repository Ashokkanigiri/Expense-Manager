package com.ashok.kanigiri.expensemanager.service.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true)
    var expenseCategoryId: Int = 0,
    val expenseCategoryTargetPrice: Double?,
    var totalUtilizedPrice: Double,
    val expenseCategoryName: String,
    val createdDate: String,
    var expenseMonthId: Int,
    var isSelected: Boolean
)