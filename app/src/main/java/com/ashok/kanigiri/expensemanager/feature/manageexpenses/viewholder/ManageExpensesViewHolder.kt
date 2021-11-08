package com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.ashok.kanigiri.expensemanager.databinding.ItemExpenseManagerBinding
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewmodel.ManageExpensesViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory

class ManageExpensesViewHolder(
    val viewmodel: ManageExpensesViewModel,
    val binding: ItemExpenseManagerBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(expenseCategory: ExpenseCategory) {
        binding.expense = expenseCategory
        binding.viewmodel = viewmodel
        binding.imageView2.setOnClickListener {
            viewmodel.openCreateExpenseBottomSheet(expenseCategory, adapterPosition)
        }
    }
}