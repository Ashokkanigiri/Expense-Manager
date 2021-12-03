package com.ashok.kanigiri.expensemanager.feature.choosecategory

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ConcatAdapter
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ChooseCategoryViewModel @Inject constructor( val roomRepository: RoomRepository, @ApplicationContext val context: Context) :
    ViewModel() {

    val addAdapter = ChooseCategoryAddAdapter(this)
    val itemAdapter = ChooseCategoryItemdapter(this)
    val concatAdapter = ConcatAdapter(addAdapter, itemAdapter)
    val event = SingleLiveEvent<ChooseCategoryViewmodelEvent>()
    var selectedCategorys : List<ExpenseCategory>? = null

    init {
        SharedPreferenceService.putBoolean(SharedPreferenceService.IS_USER_CHOOSED_CATEGORYS, false, context)

    }
    fun setAdapter(): ConcatAdapter {
        return concatAdapter
    }

    fun submitAddAdapterData() {
        addAdapter.submitList(listOf(true))
    }

    fun submitItemAdapterData(list: List<ExpenseCategory>) {
        itemAdapter.submitList(list)
    }

    fun getDefaultCategoryList(): List<ExpenseCategory>{
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
            val expenseCategory = ExpenseCategory(
                expenseCategoryTargetPrice = null,
                totalUtilizedPrice = 0.0,
                expenseCategoryName = it,
                createdDate = System.currentTimeMillis().toString(),
                expenseMonthId = 1,
                isSelected = false
            )
            categoryList.add(expenseCategory)
        }
        return categoryList
    }

    fun injectDefaultCategorysList() {
        viewModelScope.launch (Dispatchers.IO){
            Log.d("wflwfmwlfm", "DATA:: ${roomRepository.getCategoryDao().getAllExpenseCategorysRaw()?.size?:0}")
            if(roomRepository.getCategoryDao().getAllExpenseCategorysRaw()?.size?:0 <= 0){
                roomRepository.getCategoryDao().insert(getDefaultCategoryList())
            }
        }
    }

    fun openCreateExpenseDialog() {
        event.postValue(ChooseCategoryViewmodelEvent.OpenCreateExpenseDialog)
    }

    fun createAccount() {
        if(selectedCategorys?.size?:0 >= 3 ){
            event.postValue(ChooseCategoryViewmodelEvent.NavigateToEditExpenses)
        }else{
            event.postValue(ChooseCategoryViewmodelEvent.ShowSnackBar)
        }
    }

    fun updateCategorySelectionStatus(isCategorySelected: Boolean, expenseCategory: ExpenseCategory) {
        viewModelScope.launch(Dispatchers.IO) {
           roomRepository.getCategoryDao().updateCategorySelectionStatus(expenseCategory.expenseCategoryId, isCategorySelected)
        }
    }

    fun insertNewCategory(categoryName: String) {
        val expenseCategory = ExpenseCategory(
            expenseCategoryTargetPrice = null,
            totalUtilizedPrice = 0.0,
            expenseCategoryName = categoryName,
            createdDate = System.currentTimeMillis().toString(),
            expenseMonthId = 1,
            isSelected = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getCategoryDao().insert(expenseCategory)
        }
    }

    fun cancelButtonClicked(){
        event.postValue(ChooseCategoryViewmodelEvent.HandleCancelButtonClicked)
    }


}