package com.ashok.kanigiri.expensemanager.feature.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
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
        handleNavigation()
    }

    private fun handleNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph_welcome)

        val isUserEnterSalaryDetails = SharedPreferenceService.getBoolean(SharedPreferenceService.IS_USER_FILLED_CREATE_ACCOUNT_DETAILS, this)
        val isUserChoosedCategorys = SharedPreferenceService.getBoolean(SharedPreferenceService.IS_USER_CHOOSED_CATEGORYS, this)
        val isUserEditedCategorys = SharedPreferenceService.getBoolean(SharedPreferenceService.IS_USER_EDITED_CATEGORYS, this)

        if (isUserEnterSalaryDetails && isUserChoosedCategorys && isUserEditedCategorys){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {
            if(!isUserEnterSalaryDetails){
                graph.setStartDestination(R.id.createAccountFragment)
            }else if (!isUserChoosedCategorys){
                graph.setStartDestination(R.id.chooseCategoryFragment)
            }else if(!isUserEditedCategorys){
                graph.setStartDestination(R.id.editExpenseFragment)
            }
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }
}