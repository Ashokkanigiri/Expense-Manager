package com.ashok.kanigiri.expensemanager.feature.choosecategory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ConcatAdapter
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ChooseCategoryViewModel @Inject constructor(private val roomRepository: RoomRepository): ViewModel() {

    val addAdapter = ChooseCategoryAddAdapter(this)
    val itemAdapter = ChooseCategoryItemdapter(this)
    val concatAdapter = ConcatAdapter(addAdapter, itemAdapter)
    val event = SingleLiveEvent<ChooseCategoryViewmodelEvent>()
    private val seletedExpenseCategorys = ArrayList<ExpenseCategory>()

    fun setAdapter(): ConcatAdapter{
        return concatAdapter
    }

    fun submitAddAdapterData(){
        addAdapter.submitList(listOf(true))
    }

    fun submitItemAdapterData(list: List<ExpenseCategory>){
        itemAdapter.submitList(list)
    }

    fun injectDefaultCategorysList(){
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
        val categoryList = ArrayList<ExpenseCategory>()
        list.forEach {
            val expenseCategory = ExpenseCategory(UUID.randomUUID().toString(), null, null, it, System.currentTimeMillis().toString(), false)
            categoryList.add(expenseCategory)
        }
        viewModelScope.launch (Dispatchers.IO) {
            roomRepository.getCategoryDao().insert(categoryList)
        }
    }

    fun openCreateExpenseDialog(){
        event.postValue(ChooseCategoryViewmodelEvent.OpenCreateExpenseDialog)
    }


    fun createAccount(){

    }

    fun getAllCategorys(): LiveData<List<ExpenseCategory>> {
        return roomRepository.getCategoryDao().getAllExpenses()
    }

    fun updateCategorySelectionStatus(isCategorySelected: Boolean, expenseCategoryId: String){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getCategoryDao().updateCategoryUpdationStatus(isCategorySelected, expenseCategoryId)
        }
    }

    fun insertNewCategory(categoryName: String){
        val expenseCategory = ExpenseCategory(UUID.randomUUID().toString(), null, null, categoryName, System.currentTimeMillis().toString(), true)
        viewModelScope.launch (Dispatchers.IO){
            roomRepository.getCategoryDao().insert(expenseCategory)
        }
    }

}