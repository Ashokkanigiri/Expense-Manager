package com.ashok.kanigiri.expensemanager.service

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceService {

    private val APP_SHARED_PREFS = "expense-app-shared-prefs"
    private val USER_SALARY_KEY = "user-salary-key"

    fun putUserSalary(context: Context, salary: Int){
        INSTANCE(context).edit().putInt(USER_SALARY_KEY, salary).apply()
    }

    fun getUserSalary(context: Context): Int{
        return INSTANCE(context).getInt(USER_SALARY_KEY, 0)
    }

    private fun INSTANCE(context: Context): SharedPreferences{
        return context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE)
    }
}