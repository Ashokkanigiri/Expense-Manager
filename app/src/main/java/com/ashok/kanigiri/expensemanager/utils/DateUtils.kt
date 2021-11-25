package com.ashok.kanigiri.expensemanager.utils

import android.util.Log
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val TIME_IN_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_FORMAT_1 = "dd-MM-yyyy"
    private const val DATE_FORMAT_2 = "MMM dd yyyy"
    //DateFormat.getDateInstance().format(date)  -> FR Locale : 3 nov. 2017
    //val dd  = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))

    fun convertDateToDateFormat(date: Long): String {
        return  SimpleDateFormat(DATE_FORMAT_2).format(date)
    }
}