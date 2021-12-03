package com.ashok.kanigiri.expensemanager.feature.choosecategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.ItemAddChooseCategoryBinding
import com.ashok.kanigiri.expensemanager.databinding.ItemChooseCategoryBinding
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import kotlin.math.exp

class ChooseCategoryItemdapter constructor(val viewmodel: ChooseCategoryViewModel): ListAdapter <ExpenseCategory, ChooseCategoryItemViewHolder>(ChooseCategoryItemDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseCategoryItemViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemChooseCategoryBinding>(inflator, R.layout.item_choose_category, parent, false)
        return ChooseCategoryItemViewHolder(binding, viewmodel)
    }

    override fun onBindViewHolder(holder: ChooseCategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ChooseCategoryItemViewHolder(val binding: ItemChooseCategoryBinding, val viewmodel: ChooseCategoryViewModel): RecyclerView.ViewHolder(binding.root){
    fun bind(expenseLitral: ExpenseCategory){
        binding.expense = expenseLitral
        binding.viewmodel = viewmodel
        binding.viewholder = this

        binding.clMainLayout.setOnClickListener {
            viewmodel.updateCategorySelectionStatus(true, expenseLitral)
        }
        binding.clCheckedLayout.setOnClickListener {
            viewmodel.updateCategorySelectionStatus(false, expenseLitral)
        }
    }
}

class ChooseCategoryItemDiffUtil: DiffUtil.ItemCallback<ExpenseCategory>(){
    override fun areItemsTheSame(oldItem: ExpenseCategory, newItem: ExpenseCategory): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExpenseCategory, newItem: ExpenseCategory): Boolean {
        return oldItem == newItem
    }

}