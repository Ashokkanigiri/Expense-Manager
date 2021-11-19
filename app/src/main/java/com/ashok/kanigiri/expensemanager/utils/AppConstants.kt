package com.ashok.kanigiri.expensemanager.utils

import android.graphics.Color.rgb
import com.github.mikephil.charting.utils.ColorTemplate

object AppConstants {
    val CATEGORY_ID_KEY = "categoryId"
    val CATEGORY_NAME_KEY = "categoryName"
    val EXPENSE_CATEGORY_ID = "expenseCategoryId"
    val IS_EXPENSE_CREATED_KEY = "is-expense-created"
    val SHOULD_SHOW_EXPENSE_PRICE = "shouldShowExpensePrice"
    val SEND_CREATED_EXPENSE_KEY = "send_created_expense_key"
    val graphColours = arrayListOf(
        ColorTemplate.rgb("#2f4f4f"),
        ColorTemplate.rgb("#f88b62"),
        ColorTemplate.rgb("#44ab9d"),
        ColorTemplate.rgb("#ffa500"),
        ColorTemplate.rgb("#ffdab9"),
        ColorTemplate.rgb("#a9a8a2"),
        ColorTemplate.rgb("#d9c2dd"),
        ColorTemplate.rgb("#c19e9e")
    )
}