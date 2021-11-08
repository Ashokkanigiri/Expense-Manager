package com.ashok.kanigiri.expensemanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExpenseManagerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}