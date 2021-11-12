package com.ashok.kanigiri.expensemanager.service

import android.content.Context
import android.content.SharedPreferences
import com.ashok.kanigiri.expensemanager.service.models.UserLoginModel
import com.google.gson.Gson

object SharedPreferenceService {

    private val APP_SHARED_PREFS = "expense-app-shared-prefs"
    private val USER_SALARY_KEY = "user-salary-key"
    private val USER_LOGIN_MODEL = "user-login-model"

    fun putUserSalary(context: Context, salary: Int){
        INSTANCE(context).edit().putInt(USER_SALARY_KEY, salary).apply()
    }

    fun putUserLoginModel(context: Context, userLoginModel: UserLoginModel){
        val gson = Gson()
        INSTANCE(context).edit().putString(USER_LOGIN_MODEL, gson.toJson(userLoginModel)).apply()
    }

    fun getUserLoginModel(context: Context): UserLoginModel{
        val gson = Gson()
        val json =  INSTANCE(context).getString(USER_LOGIN_MODEL, "")
        return gson.fromJson(json, UserLoginModel::class.java)
    }


    fun getUserSalary(context: Context): Int{
        return INSTANCE(context).getInt(USER_SALARY_KEY, 0)
    }

    private fun INSTANCE(context: Context): SharedPreferences{
        return context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE)
    }
}