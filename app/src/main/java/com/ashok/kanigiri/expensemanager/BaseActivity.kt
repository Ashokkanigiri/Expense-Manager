package com.ashok.kanigiri.expensemanager

import android.graphics.drawable.Drawable
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
        this.actionBar.ivTrailing2.visibility = View.GONE
        this.actionBar.ivTrailing3.visibility = View.GONE
        this.actionBar.ivBack.visibility = View.GONE
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

    fun setTrailingIcon(drawable: Drawable){
        this.actionBar.ivTrailingIcon.visibility = View.VISIBLE
        this.actionBar.ivTrailingIcon.setImageDrawable(drawable)
    }

    fun setTrailingIcon2(drawable: Drawable){
        this.actionBar.ivTrailing2.visibility = View.VISIBLE
        this.actionBar.ivTrailing2.setImageDrawable(drawable)
    }

    fun setTrailingIcon3(drawable: Drawable){
        this.actionBar.ivTrailing3.visibility = View.VISIBLE
        this.actionBar.ivTrailing3.setImageDrawable(drawable)
    }

    fun handleTrailingIcon2ClickListener(listener: View.OnClickListener){
        this.actionBar.ivTrailing2.setOnClickListener(listener)
    }

    fun handleTrailingIcon3ClickListener(listener: View.OnClickListener){
        this.actionBar.ivTrailing3.setOnClickListener(listener)
    }

    fun handleTrailingIconClickListener(listener: View.OnClickListener){
        this.actionBar.ivTrailingIcon.setOnClickListener(listener)
    }

    fun handleBackButtonClickListener(listener: View.OnClickListener){
        this.actionBar.ivBack.visibility = View.VISIBLE
        this.actionBar.ivBack.setOnClickListener(listener)
    }
}