package com.ashok.kanigiri.expensemanager.feature.allexpenses

import androidx.recyclerview.widget.RecyclerView
import com.ashok.kanigiri.expensemanager.databinding.LayoutItemAllExpensesBinding
import com.ashok.kanigiri.expensemanager.databinding.LayoutItemExpenseListBinding
import com.ashok.kanigiri.expensemanager.feature.expenseList.viewmodel.ExpenseListViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense

class AllExpensesListViewHolder(val binding: LayoutItemAllExpensesBinding, val viewmodel: AllExpensesViewmodel): RecyclerView.ViewHolder(binding.root) {

    fun bind(expense:Expense){
        binding.expense = expense
        binding.viewmodel = viewmodel
    }
}