package com.ashok.kanigiri.expensemanager.service.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = ["expenseCategoryName"], unique = true)))
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true)
    val expenseCategoryId: Int = 0,
    val expenseCategoryTargetPrice: Double?,
    val totalUtilizedPrice: Double,
    val expenseCategoryName: String,
    val createdDate: String,
    val expenseMonthId: Int,
    var isSelected: Boolean
)