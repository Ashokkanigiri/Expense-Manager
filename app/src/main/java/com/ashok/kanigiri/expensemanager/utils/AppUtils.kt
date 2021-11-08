package com.ashok.kanigiri.expensemanager.utils

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes

object AppUtils {

    fun findExpenseType(expenseCategory: String) = when(expenseCategory){
        ExpenseTypes.FOOD.expenseLitral -> ExpenseTypes.FOOD
        ExpenseTypes.SHOPPING.expenseLitral -> ExpenseTypes.SHOPPING
        ExpenseTypes.GYM.expenseLitral ->  ExpenseTypes.GYM
        ExpenseTypes.MEDICAL.expenseLitral -> ExpenseTypes.MEDICAL
        ExpenseTypes.HOUSE_RENT.expenseLitral -> ExpenseTypes.HOUSE_RENT
        ExpenseTypes.TRAVEL.expenseLitral -> ExpenseTypes.TRAVEL
        ExpenseTypes.FREE_HAND_MONEY.expenseLitral ->  ExpenseTypes.FREE_HAND_MONEY
        ExpenseTypes.INVESTING.expenseLitral -> ExpenseTypes.INVESTING
        ExpenseTypes.MONTHLY_EMI.expenseLitral -> ExpenseTypes.MONTHLY_EMI
        ExpenseTypes.MISCELLANEOUS.expenseLitral -> ExpenseTypes.MISCELLANEOUS
        else -> ExpenseTypes.MISCELLANEOUS
    }

    fun getSelectedDateFromDatePicker(context: Context): MutableLiveData<String>{
        val date = MutableLiveData<String>()
        val datePickerDialog = DatePickerDialog(context)
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            date.postValue("$dayOfMonth/$month/$year")
        }
        datePickerDialog.show()
        return date
    }
}