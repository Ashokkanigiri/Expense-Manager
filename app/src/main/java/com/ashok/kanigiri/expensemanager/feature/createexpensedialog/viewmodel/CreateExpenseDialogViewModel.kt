package com.ashok.kanigiri.expensemanager.feature.createexpensedialog.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.kanigiri.expensemanager.service.room.entity.Expense
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateExpenseDialogViewModel @Inject constructor(private val roomRepository: RoomRepository,@ApplicationContext val applicationContext: Context): ViewModel() {

    val showCalenderEvent = SingleLiveEvent<Boolean>()
    val hideDialog = SingleLiveEvent<Boolean>()
    val showErrorMsg = SingleLiveEvent<String>()
    var expenseId: String? = null
    var expenseName: String? = null
    var selectedDate: String? = null
    var expensePrice: String? = null
    var isExpenseCreated = false

    init {
        hideDialog.postValue(false)
    }

    fun showCalenderPicker(){
        showCalenderEvent.postValue(true)
    }

    fun createExpense(){
        if(expensePrice?.trim()!= null && expensePrice?.trim() != "" && selectedDate?.trim() != "" && selectedDate?.trim()!= null){
            val utilizedExpense =roomRepository.getExpenseDao().getUtilizedPriceForCategory(expenseId!!)
            val totalCategoryPrice = roomRepository.getCategoryDao().getTotalExpensePriceForCategory(expenseId!!)
            if(((expensePrice?.toDouble())?.plus(utilizedExpense)?:0.0) <= totalCategoryPrice){
                Log.d("wkmwkm", "Utilized expense")
                val expense = Expense(expenseCategoryId = expenseId?:"", expenseId = UUID.randomUUID().toString(), expenseName = expenseName?:"", createdDate = selectedDate?:"", expensePrice = expensePrice?.toDouble()?:0.0)
                viewModelScope.launch (Dispatchers.IO){
                    roomRepository.getExpenseDao().insertExpenses(expense)
                    roomRepository.getCategoryDao().updateUtilizedPriceForCategory(expense.expenseCategoryId, expense.expensePrice)

                    hideDialog.postValue(true)
                    isExpenseCreated = true
                }
            }else{
                val value = totalCategoryPrice -(utilizedExpense)
                if(value == 0.0){
                    showErrorMsg.postValue("MAXIMUM LIMIT REACHED: You reached maximum limit for this category")
                }else{
                    showErrorMsg.postValue("MAXIMUM LIMIT REACHED: You Can Add only upto $value for this category")
                }
            }
        }else{
            Toast.makeText(applicationContext, "Please Enter Valid Details", Toast.LENGTH_SHORT).show()
        }
    }
}