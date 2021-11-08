package com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.ashok.kanigiri.expensemanager.utils.Event
import com.ashok.kanigiri.expensemanager.utils.SingleLiveEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseCategoryDialogViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {

    val expenseTargetPrice = ObservableField<String>()
    val expenseCategoryName = ObservableField<String>()
    val event = SingleLiveEvent<Event<Boolean>>()

    fun insertExpenseCategory() {
        if (expenseTargetPrice.get()?.trim() != "" && expenseTargetPrice.get()?.trim() != null) {
            val expenseCategory = ExpenseCategory(
                expenseCategoryId = UUID.randomUUID().toString(),
                expenseCategoryTargetPrice = expenseTargetPrice.get()?.toDouble()?:0.0,
                totalUtilizedPrice = 0.0,
                expenseType = AppUtils.findExpenseType(expenseCategoryName.get() ?: ""),
                createdDate = System.currentTimeMillis().toString()
            )
            Log.d(
                "ajafjaf",
                "BEFORE :::::: inserting ExpenseCat: ${Gson().toJson(expenseCategory)}"
            )

            GlobalScope.launch(Dispatchers.IO) {
                roomRepository.getCategoryDao().insert(expenseCategory)
                Log.d("ajafjaf", "inserting ExpenseCat: ${Gson().toJson(expenseCategory)}")
            }
        }
        event.postValue(Event(true))
    }

    fun onExpenseTargetPriceChanged(char: CharSequence) {
        expenseTargetPrice.set(char.toString())
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ajafjaf", "ON CLEARED")

    }

}