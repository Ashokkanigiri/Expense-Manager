package com.ashok.kanigiri.expensemanager.feature.expenseList.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentExpenseListBinding
import com.ashok.kanigiri.expensemanager.feature.expenseList.viewmodel.ExpenseListViewModel
import com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.view.ExpenseCategoryDialogFragment
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseListFragment: Fragment() {

    lateinit var binding:LayoutFragmentExpenseListBinding
    private val viewmodel: ExpenseListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_expense_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        loadArguments()
        observeViewmodel()
    }

    private fun observeViewmodel() {
        viewmodel.getAllExpenses(viewmodel.expenseCategoryId?:"").observe(viewLifecycleOwner, Observer {
            Log.d("aaaa", "III:: ${Gson().toJson(it)}")
            viewmodel.submitList(it)
        })
    }

    private fun loadArguments() {
        arguments?.getString(AppConstants.EXPENSE_CATEGORY_ID)?.let {
            viewmodel.expenseCategoryId = it
        }
        arguments?.getString(AppConstants.CATEGORY_NAME_KEY)?.let {
            viewmodel.categoryName = it
            setupActionBar(viewmodel.categoryName)
        }
    }

    private fun setupActionBar(categoryName: String?) {
        (activity as BaseActivity).setUpActitionBar(binding.custumActionBar)
        (activity as BaseActivity).setActionBarTitle(categoryName?:"", Gravity.START)
    }
}