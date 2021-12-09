package com.ashok.kanigiri.expensemanager.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import java.lang.StringBuilder
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
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

    fun getSelectedDateFromDatePicker(context: Context?): MutableLiveData<Long>{
        val date = MutableLiveData<Long>()
        context?.let {

            val datePickerDialog = DatePickerDialog(context)

            val fromCal = Calendar.getInstance()
            fromCal.set(Calendar.DAY_OF_MONTH, 1)
            fromCal.set(Calendar.MONTH, (SimpleDateFormat("MM").format(Date()).toInt() ?:0)-1)
            fromCal.set(Calendar.YEAR, SimpleDateFormat("YYYY").format(Date()).toInt() ?:0)

            val toCal = Calendar.getInstance()
            toCal.set(Calendar.DAY_OF_MONTH, getLastDayOf((SimpleDateFormat("MM").format(Date()).toInt())-1, (SimpleDateFormat("YYYY").format(Date()).toInt())))
            toCal.set(Calendar.MONTH, (SimpleDateFormat("MM").format(Date()).toInt() ?:0)-1)
            toCal.set(Calendar.YEAR, SimpleDateFormat("YYYY").format(Date()).toInt() ?:0)

            Log.d("lmflwm", "FROM CAL : ${Timestamp(fromCal.timeInMillis)}, TOCAL : ${Timestamp(toCal.timeInMillis)}")

            datePickerDialog.datePicker.minDate =fromCal.timeInMillis
            datePickerDialog.datePicker.maxDate =toCal.timeInMillis
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->

                val ddd = "$dayOfMonth/${month+1}/$year"

                val selectedDatePicker = Calendar.getInstance()
                selectedDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDatePicker.set(Calendar.MONTH, month)
                selectedDatePicker.set(Calendar.YEAR, year)
                date.postValue(Timestamp(selectedDatePicker.timeInMillis).time)

            }
            datePickerDialog.show()
        }

        return date
    }

    fun getSelectedDateByDatePickerNormal(context: Context?): MutableLiveData<Long>{
        val date = MutableLiveData<Long>()
        context?.let {

            val datePickerDialog = DatePickerDialog(context)

            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->

                val selectedDatePicker = Calendar.getInstance()
                selectedDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDatePicker.set(Calendar.MONTH, month)
                selectedDatePicker.set(Calendar.YEAR, year)
                date.postValue(Timestamp(selectedDatePicker.timeInMillis).time)

            }
            datePickerDialog.show()
        }

        return date
    }

    fun getFirstDayOnMonthInDateFormat(): String{
        return "${SimpleDateFormat("YYYY", Locale.getDefault()).format(Date())}-${(SimpleDateFormat("MM", Locale.getDefault()).format(Date()))}-${"01"}"
    }

    fun getCurrentMonthInInt() = (SimpleDateFormat("MM", Locale.getDefault()).format(Date()).toInt()
        ?:0)

    fun getLastDayOfMonthInDateFormat(): String{
        return "${(SimpleDateFormat("YYYY", Locale.getDefault()).format(Date()))}-${(SimpleDateFormat("MM", Locale.getDefault()).format(Date()))}-${getLastDayOf((SimpleDateFormat("MM", Locale.getDefault()).format(Date()).toInt())-1, (SimpleDateFormat("YYYY").format(Date()).toInt()))}"
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
        return monthNames.get(SimpleDateFormat("MM", Locale.getDefault()).format(Date()).toInt() -1)
    }

    fun getNextMonthDate(currentDate: String?): String{
        val day = 1
        val formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-d")
        var date = LocalDate.parse(currentDate, formatter1)
        date = date?.plusMonths(2)
        val currentMonth: Int = date.month?.value?:0+2
        val currentYear :Int= date.year
        return "${currentYear}-${currentMonth}-${day}"
    }

    fun getCurrentDateInFormat(): String{
        val date = LocalDate.now()
        val currentMonth: Int = date.month?.value?:0
        val currentYear :Int= date.year
        val day = date.dayOfMonth
        return "${currentYear}-${currentMonth}-${day}"
    }

    fun getCurrentDateInRoomFormat(): String{
        val date = LocalDate.now()
        val currentMonth: Int = date.month?.value?:0
        val currentYear :Int= date.year
        val sbm = StringBuilder()
        if(currentMonth.toString().toCharArray().size <= 1){
            sbm.append(0)
            sbm.append(currentMonth)
        }else{
            sbm.append(currentMonth)
        }
        return "${currentYear}-${sbm}-01"
    }

    fun getPreviousExpenseMonthDate(): String{
        var date = LocalDate.now()
        date = date?.minusMonths(1)
        val currentMonth: Int = date.month?.value?:0+2
        val currentYear :Int= date.year

        val sbm = StringBuilder()
        if(currentMonth.toString().toCharArray().size <= 1){
            sbm.append(0)
            sbm.append(currentMonth)
        }else{
            sbm.append(currentMonth)
        }
        return "${currentYear}-${sbm}-01"
    }


    fun getUpcommingExpenseMonthUpdationDate(currentDate: String?): String{
        val formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var date = LocalDate.parse(currentDate, formatter1)
        date = date?.plusMonths(1)
        val currentMonth: Int = date.month.value
        val currentYear :Int= date.year

       return "${currentYear}-${currentMonth}-01"
    }

    fun shouldUpdateToNextMonth(savedDate: String?): Boolean{
        return getCurrentDateInFormat() >= getUpcommingExpenseMonthUpdationDate(savedDate)
    }
}