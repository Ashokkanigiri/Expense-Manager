package com.ashok.kanigiri.expensemanager.feature.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.MainActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        (SharedPreferenceService.getUserLoginModel(this)?.salary)?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}