package com.ashok.kanigiri.expensemanager.feature.choosecategory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseCategoryViewModel @Inject constructor(): ViewModel() {

    val addAdapter = ChooseCategoryAddAdapter(this)
    val itemAdapter = ChooseCategoryItemdapter(this)
    val concatAdapter = ConcatAdapter(addAdapter, itemAdapter)
    val event = SingleLiveEvent<ChooseCategoryViewmodelEvent>()
    private val seletedExpenseCategorys = ArrayList<String>()

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

    fun openCreateExpenseDialog(){
        event.postValue(ChooseCategoryViewmodelEvent.OpenCreateExpenseDialog)
    }

    fun addCategoryToSelectedList(list: List<String>){
        seletedExpenseCategorys.addAll(list)
    }

    fun removeFromSelectedCategoryList(category: String){
        seletedExpenseCategorys.remove(category)
    }

    fun createAccount(){
        Log.d("mndwndwk", "Selected Categorys : ${Gson().toJson(seletedExpenseCategorys)}")
    }
}