package com.ashok.kanigiri.expensemanager.feature.manageexpenses.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    lateinit var binding: LayoutFragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
    }

    private fun setupActionBar() {
        (activity as BaseActivity).setUpActitionBar(binding.custumActionBar)
        (activity as BaseActivity).setActionBarTitle("My Profile", Gravity.START)
    }
}