package com.ashok.kanigiri.expensemanager.feature.choosecategory

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseCategoryViewModel @Inject constructor(): ViewModel() {

    val addAdapter = ChooseCategoryAddAdapter()
    val itemAdapter = ChooseCategoryItemdapter()
    val concatAdapter = ConcatAdapter(addAdapter, itemAdapter)

    fun setAdapter(): ConcatAdapter{
        return concatAdapter
    }

    fun submitAddAdapterData(){
        addAdapter.submitList(listOf(true))
    }
    fun submitItemAdapterData(list: List<String>){
        itemAdapter.submitList(list)
    }

    fun getDefaultCategorysList(): List<String>{
        val list = ArrayList<String>()
        list.add(ExpenseTypes.FOOD.expenseLitral)
        list.add(ExpenseTypes.GYM.expenseLitral)
        list.add(ExpenseTypes.HOUSE_RENT.expenseLitral)
        list.add(ExpenseTypes.INVESTING.expenseLitral)
        list.add(ExpenseTypes.MEDICAL.expenseLitral)
        list.add(ExpenseTypes.MONTHLY_EMI.expenseLitral)
        list.add(ExpenseTypes.SHOPPING.expenseLitral)
        list.add(ExpenseTypes.TRAVEL.expenseLitral)
        list.add(ExpenseTypes.MISCELLANEOUS.expenseLitral)
        list.add(ExpenseTypes.FREE_HAND_MONEY.expenseLitral)
        return list
    }

}