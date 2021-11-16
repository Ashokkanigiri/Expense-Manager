package com.ashok.kanigiri.expensemanager.feature.allexpenses

import androidx.recyclerview.widget.DiffUtil
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense

class AllExpensesDiffUtil: DiffUtil.ItemCallback<Expense>() {
    override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
        return oldItem.expenseId == newItem.expenseId
    }
}