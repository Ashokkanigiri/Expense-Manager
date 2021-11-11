package com.ashok.kanigiri.expensemanager.feature.editexpenses

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.ItemEditExpensesBinding
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory

class EditExpensesAdapter constructor(val viewmodel: EditExpensesViewmodel): ListAdapter<ExpenseCategory, EditExpensesViewHolder>(EditExpensesDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditExpensesViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemEditExpensesBinding>(inflator, R.layout.item_edit_expenses, parent, false)
        return EditExpensesViewHolder(binding, viewmodel, this)
    }

    override fun onBindViewHolder(holder: EditExpensesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).expenseCategoryId.toLong()
    }
}

class EditExpensesViewHolder(
    val binding: ItemEditExpensesBinding,
    val viewmodel: EditExpensesViewmodel,
    val adapter: EditExpensesAdapter
): RecyclerView.ViewHolder(binding.root){
    fun bind(expenseCategory: ExpenseCategory){
        binding.expense = expenseCategory
        binding.seekbarMax = viewmodel.salary
        binding.seebarProgress = viewmodel.getTargetPriceForCategory(expenseCategory.expenseCategoryId).toInt()
        binding.sbExpense.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d("wkfnwkfnw", "onProgressChanged ")
                binding.textView15.text=""+progress
                viewmodel.progressMap.put(expenseCategory.expenseCategoryName, seekBar?.progress?:0)
                viewmodel.calculateTotalExpenses()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.d("wkfnwkfnw", "onStartTrackingTouch ")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d("wkfnwkfnw", "||||||onStopTrackingTouch ${seekBar?.progress}")
                viewmodel.updateTargetPriceForCategory(expenseCategory.expenseCategoryId, seekBar?.progress?.toDouble()?:0.0)
            }
        })
    }
}

class EditExpensesDiffUtil: DiffUtil.ItemCallback<ExpenseCategory>(){
    override fun areItemsTheSame(oldItem: ExpenseCategory, newItem: ExpenseCategory): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExpenseCategory, newItem: ExpenseCategory): Boolean {
        return oldItem.expenseCategoryId == newItem.expenseCategoryId
    }
}