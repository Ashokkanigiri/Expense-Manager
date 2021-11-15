package com.ashok.kanigiri.expensemanager.feature.expenseList.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ashok.kanigiri.expensemanager.databinding.LayoutItemExpenseListBinding
import com.ashok.kanigiri.expensemanager.feature.expenseList.viewmodel.ExpenseListViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense

class ExpenseListViewHolder(val binding: LayoutItemExpenseListBinding, val viewmodel: ExpenseListViewModel): RecyclerView.ViewHolder(binding.root) {

    fun bind(expense:Expense){
        binding.expense = expense
        binding.viewmodel = viewmodel
    }
}