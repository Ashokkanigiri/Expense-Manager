package com.ashok.kanigiri.expensemanager.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.MainActivity
import com.ashok.kanigiri.expensemanager.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewmodel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewmodel.event.observe(this, Observer { it ->
            when (it.getContentIfNotHandled()) {
                is SplashViewModelEvent.NavigateToMainFragment -> {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
            }
        })
    }
}