package com.ashok.kanigiri.expensemanager

import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ashok.kanigiri.expensemanager.databinding.CustumActionBarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity: AppCompatActivity() {

    lateinit var actionBar: CustumActionBarBinding

    private fun initActionBar(){
        this.actionBar.ivTrailingIcon.visibility = View.GONE
    }

    fun setUpActitionBar(actionBarBinding: CustumActionBarBinding){
        this.actionBar = actionBarBinding
        initActionBar()
    }

    fun setActionBarTitle(title: String, gravity: Int){
        this.actionBar.tvActionbarTitle.text = title
        this.actionBar.tvActionbarTitle.gravity = gravity
    }

    fun enableTrailingIconVisibility(){
        this.actionBar.ivTrailingIcon.visibility = View.VISIBLE
    }

    fun handleTrailingIconVisibility(listener: View.OnClickListener){
        this.actionBar.ivTrailingIcon.setOnClickListener(listener)
    }
}