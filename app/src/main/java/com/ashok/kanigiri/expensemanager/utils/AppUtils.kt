package com.ashok.kanigiri.expensemanager.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.absoluteValue

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

        val fromCal = Calendar.getInstance()
        fromCal.set(Calendar.DAY_OF_MONTH, 1)
        fromCal.set(Calendar.MONTH, (SimpleDateFormat("MM").format(Date())?.toInt()?:0)-1)
        fromCal.set(Calendar.YEAR, SimpleDateFormat("YYYY").format(Date())?.toInt()?:0)

        val toCal = Calendar.getInstance()
        toCal.set(Calendar.DAY_OF_MONTH, getLastDayOf((SimpleDateFormat("MM").format(Date()).toInt())-1, (SimpleDateFormat("YYYY").format(Date()).toInt())))
        toCal.set(Calendar.MONTH, (SimpleDateFormat("MM").format(Date())?.toInt()?:0)-1)
        toCal.set(Calendar.YEAR, SimpleDateFormat("YYYY").format(Date())?.toInt()?:0)

        Log.d("lmflwm", "FROM CAL : ${Timestamp(fromCal.timeInMillis)}, TOCAL : ${Timestamp(toCal.timeInMillis)}")

        datePickerDialog.datePicker.minDate =fromCal.timeInMillis
        datePickerDialog.datePicker.maxDate =toCal.timeInMillis
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            date.postValue("$dayOfMonth/${month+1}/$year")
        }
        datePickerDialog.show()
        return date
    }

    fun getFirstDayOnMonthInDateFormat(): String{
        val fromCal = Calendar.getInstance()
        fromCal.set(Calendar.DAY_OF_MONTH, "01".toInt())
        fromCal.set(Calendar.MONTH, (SimpleDateFormat("MM").format(Date())?.toInt()?:0)-1)
        fromCal.set(Calendar.YEAR, SimpleDateFormat("YYYY").format(Date())?.toInt()?:0)
        return "${fromCal.get(Calendar.YEAR)}-${fromCal.get(Calendar.MONTH)}-${fromCal.get(Calendar.DAY_OF_MONTH)}"
    }

    fun getCurrentMonthInInt() = (SimpleDateFormat("MM").format(Date())?.toInt()?:0)

    fun getLastDayOfMonthInDateFormat(): String{
        val toCal = Calendar.getInstance()
        toCal.set(Calendar.DAY_OF_MONTH, getLastDayOf((SimpleDateFormat("MM").format(Date()).toInt())-1, (SimpleDateFormat("YYYY").format(Date()).toInt())))
        toCal.set(Calendar.MONTH, (SimpleDateFormat("MM").format(Date())?.toInt()?:0)-1)
        toCal.set(Calendar.YEAR, SimpleDateFormat("YYYY").format(Date())?.toInt()?:0)
        return "${toCal.get(Calendar.YEAR)}-${toCal.get(Calendar.MONTH)}-${toCal.get(Calendar.DAY_OF_MONTH)}"
    }

    fun getLastDayOf(month: Int, year: Int): Int {
        return when (month) {
            Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
            Calendar.FEBRUARY -> {
                if (year % 4 == 0) {
                    29
                } else 28
            }
            else -> 31
        }
    }

    fun getCurrentMonth(): String{
        val monthNames = arrayListOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        return monthNames.get((SimpleDateFormat("MM").format(Date()).toInt()?:0)-1)
    }

    fun getDateInReadableFormat(currentDate: String): String{
        val day = 1
        val formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-d")
        val date = LocalDate.parse(currentDate, formatter1)
        val currentMonth: Int = date.month?.value?:0+1
        val currentYear :Int= date.year
        return "${currentYear}-${currentMonth}-${day}"
    }


    fun getUpcommingExpenseMonthUpdationDate(currentDate: String): String{
        val day = 1
        val formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-d")
        val date = LocalDate.parse(currentDate, formatter1)
        var currentMonth: Int = date.month?.value?:0+1
        var currentYear :Int= date.year
        if(currentMonth == 12){
            currentYear = currentYear+1
            currentMonth = 1
        }else{
            currentMonth = currentMonth+1
        }
       return "${currentYear}-${currentMonth}-${day}"
    }

    fun shouldUpdateToNextMonth(savedDate: String): Boolean{
        //if savedDate > getUpcommingMonthStarttingDate -> then return true else false
        Log.d("ndwdwkd", "UPCOMMING MONTH ${getUpcommingExpenseMonthUpdationDate(savedDate)}, CURRENT DATE : ${getDateInReadableFormat(savedDate)} ::: ${getDateInReadableFormat(savedDate) > getUpcommingExpenseMonthUpdationDate(savedDate)}")
        if(getDateInReadableFormat(savedDate) > getUpcommingExpenseMonthUpdationDate(savedDate)){
            return true
        }else{
            return false
        }
    }
}