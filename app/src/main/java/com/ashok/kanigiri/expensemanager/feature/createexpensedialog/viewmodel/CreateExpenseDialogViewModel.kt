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
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateExpenseDialogViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    @ApplicationContext val applicationContext: Context
) : ViewModel() {

    val showCalenderEvent = SingleLiveEvent<Boolean>()
    val hideDialog = SingleLiveEvent<Boolean>()
    val showErrorMsg = SingleLiveEvent<String>()
    var expenseId: Int? = null
    var expenseName: String? = null
    var selectedDate: String? = null
    var expensePrice: String? = null
    var isExpenseCreated = false

    fun getReserveCash() = roomRepository.getCategoryDao()
        .getTotalExpensePriceForCategory(expenseId!!) - (roomRepository.getExpenseDao()
        .getUtilizedPriceForCategory(expenseId!!))

    init {
        hideDialog.postValue(false)
    }

    fun showCalenderPicker() {
        showCalenderEvent.postValue(true)
    }

    fun createExpense() {
        viewModelScope.launch(Dispatchers.IO) {
            if (expensePrice?.trim() != null && expensePrice?.trim() != "" && selectedDate?.trim() != "" && selectedDate?.trim() != null) {
                val utilizedExpense = withContext(Dispatchers.IO) {
                    roomRepository.getExpenseDao().getUtilizedPriceForCategory(expenseId!!)
                }
                val totalCategoryPrice = withContext(Dispatchers.IO) {
                    roomRepository.getCategoryDao().getTotalExpensePriceForCategory(expenseId!!)
                }
                verifyExpensePriceIsNotExceedingTotalPriceAndInsert(
                    utilizedExpense,
                    totalCategoryPrice
                )
            } else {
                Toast.makeText(applicationContext, "Please Enter Valid Details", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private suspend fun verifyExpensePriceIsNotExceedingTotalPriceAndInsert(
        utilizedExpense: Double,
        totalCategoryPrice: Double
    ) {
        if (((expensePrice?.toDouble())?.plus(utilizedExpense)
                ?: 0.0) <= totalCategoryPrice
        ) {
            val expense = Expense(
                expenseCategoryId = expenseId ?: 0,
                expenseName = expenseName ?: "",
                createdDate = "${Timestamp(System.currentTimeMillis())}",
                expensePrice = expensePrice?.toDouble() ?: 0.0,
                expenseDate = selectedDate ?: "",
                expenseMonthId = roomRepository.getExpenseMonthDao()
                    .getLatestExpenseMonth()?.expenseMonthId ?: 0
            )
            roomRepository.getExpenseDao().insertExpenses(expense)
            roomRepository.getCategoryDao().updateUtilizedPriceForCategory(
                expense.expenseCategoryId,
                expense.expensePrice
            )
            hideDialog.postValue(true)
            isExpenseCreated = true
        } else {
            val value = totalCategoryPrice - (utilizedExpense)
            if (value == 0.0) {
                showErrorMsg.postValue("MAXIMUM LIMIT REACHED: You reached maximum limit for this category")
            } else {
                showErrorMsg.postValue("MAXIMUM LIMIT REACHED: You Can Add only upto $value for this category")
            }
        }
    }
}