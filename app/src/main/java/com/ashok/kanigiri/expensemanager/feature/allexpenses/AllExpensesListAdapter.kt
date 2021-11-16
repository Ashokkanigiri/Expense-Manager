package com.ashok.kanigiri.expensemanager.feature.allexpenses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutItemAllExpensesBinding
import com.ashok.kanigiri.expensemanager.databinding.LayoutItemExpenseListBinding
import com.ashok.kanigiri.expensemanager.feature.expenseList.ExpenseListDiffUtil
import com.ashok.kanigiri.expensemanager.feature.expenseList.viewholder.ExpenseListViewHolder
import com.ashok.kanigiri.expensemanager.feature.expenseList.viewmodel.ExpenseListViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense

class AllExpensesListAdapter constructor(private val viewmodel: AllExpensesViewmodel) :
    ListAdapter<Expense, AllExpensesListViewHolder>(AllExpensesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllExpensesListViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutItemAllExpensesBinding>(
            inflator,
            R.layout.layout_item_all_expenses,
            parent,
            false
        )
        return AllExpensesListViewHolder(binding, viewmodel)
    }

    override fun onBindViewHolder(holder: AllExpensesListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}