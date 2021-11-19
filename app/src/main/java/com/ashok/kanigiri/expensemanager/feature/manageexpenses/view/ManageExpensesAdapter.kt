package com.ashok.kanigiri.expensemanager.feature.manageexpenses.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.ItemExpenseManagerBinding
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewholder.ManageExpenseDiffUtil
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewholder.ManageExpensesViewHolder
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewmodel.ManageExpensesViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory

class ManageExpensesAdapter (private val viewModel: ManageExpensesViewModel) : ListAdapter<ExpenseCategory, ManageExpensesViewHolder>(ManageExpenseDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageExpensesViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemExpenseManagerBinding>(inflator, R.layout.item_expense_manager, parent, false)
        return ManageExpensesViewHolder(viewModel, binding)
    }

    override fun onBindViewHolder(holder: ManageExpensesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).expenseCategoryId.toLong()
    }
}