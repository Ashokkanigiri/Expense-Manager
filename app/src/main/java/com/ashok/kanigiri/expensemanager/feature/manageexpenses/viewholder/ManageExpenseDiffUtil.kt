package com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewholder

import androidx.recyclerview.widget.DiffUtil
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory

class ManageExpenseDiffUtil: DiffUtil.ItemCallback<ExpenseCategory>() {
    override fun areItemsTheSame(oldItem: ExpenseCategory, newItem: ExpenseCategory): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExpenseCategory, newItem: ExpenseCategory): Boolean {
        return oldItem.expenseCategoryId == newItem.expenseCategoryId
    }
}