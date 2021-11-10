package com.ashok.kanigiri.expensemanager.feature.choosecategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.ItemAddChooseCategoryBinding

class ChooseCategoryAddAdapter constructor(val viewmodel : ChooseCategoryViewModel): ListAdapter <Boolean, ChooseCategoryAddViewHolder>(ChooseCategoryDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseCategoryAddViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAddChooseCategoryBinding>(inflator, R.layout.item_add_choose_category, parent, false)
        return ChooseCategoryAddViewHolder(binding, viewmodel)
    }

    override fun onBindViewHolder(holder: ChooseCategoryAddViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ChooseCategoryAddViewHolder(val binding: ItemAddChooseCategoryBinding, val viewmodel: ChooseCategoryViewModel): RecyclerView.ViewHolder(binding.root){
    fun bind(drawable: Boolean){
        binding.viewmodel = viewmodel
    }
}

class ChooseCategoryDiffUtil: DiffUtil.ItemCallback<Boolean>(){
    override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
        return oldItem == newItem
    }

}