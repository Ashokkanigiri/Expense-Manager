package com.ashok.kanigiri.expensemanager.feature.expenseList.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutItemExpenseListBinding
import com.ashok.kanigiri.expensemanager.feature.expenseList.ExpenseListDiffUtil
import com.ashok.kanigiri.expensemanager.feature.expenseList.viewholder.ExpenseListViewHolder
import com.ashok.kanigiri.expensemanager.feature.expenseList.viewmodel.ExpenseListViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense

class ExpenseListAdapter constructor(private val viewmodel: ExpenseListViewModel) :
    ListAdapter<Expense, ExpenseListViewHolder>(ExpenseListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutItemExpenseListBinding>(
            inflator,
            R.layout.layout_item_expense_list,
            parent,
            false
        )
        return ExpenseListViewHolder(binding, viewmodel)
    }

    override fun onBindViewHolder(holder: ExpenseListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).expenseId.toLong()
    }
}