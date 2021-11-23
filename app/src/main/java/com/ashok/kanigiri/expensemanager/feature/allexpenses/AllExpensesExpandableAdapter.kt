package com.ashok.kanigiri.expensemanager.feature.allexpenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.databinding.DataBindingUtil
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutChildExpandableAllExpensesBinding

import com.ashok.kanigiri.expensemanager.databinding.LayoutHeaderExpandableAllExpensesBinding
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseMonth

class AllExpensesExpandableAdapter (val viewmodel: AllExpensesViewmodel): BaseExpandableListAdapter() {

    var data: List<Map<Int, List<Expense>>>? = null
    var keys: List<ExpenseMonth>? = null

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflator = LayoutInflater.from(parent?.context)
        val binding = DataBindingUtil.inflate<LayoutHeaderExpandableAllExpensesBinding>(inflator, R.layout.layout_header_expandable_all_expenses, parent, false)
        binding.expenseMonth = getGroup(groupPosition)
        if(isExpanded){
            binding.ivDropDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
        }else{
            binding.ivDropDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
        }
        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val inflator = LayoutInflater.from(parent?.context)
        val binding = DataBindingUtil.inflate<LayoutChildExpandableAllExpensesBinding>(inflator, R.layout.layout_child_expandable_all_expenses, parent, false)
        binding.expense = getChild(groupPosition, childPosition)
        binding.viewmodel = viewmodel
        return binding.root
    }

    override fun getGroupCount(): Int {
        return keys?.size?:0
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return data?.get(groupPosition)?.get(keys?.get(groupPosition)?.expenseMonthId?:0)?.size?:0
    }

    override fun getGroup(groupPosition: Int): ExpenseMonth? {
        return keys?.get(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Expense? {
        return data?.get(groupPosition)?.get(keys?.get(groupPosition)?.expenseMonthId?:0)?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return keys?.get(groupPosition)?.expenseMonthId?.toLong()?:0
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return data?.get(groupPosition)?.get(keys?.get(groupPosition)?:0)?.get(childPosition)?.expenseId?.toLong()?:0
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
         return true
    }

    fun setUpAdapter(data: List<Map<Int, List<Expense>>>, keys: List<ExpenseMonth>){
        this.data = data
        this.keys = keys
        notifyDataSetChanged()
    }
}