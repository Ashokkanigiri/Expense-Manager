package com.ashok.kanigiri.expensemanager.feature.allexpenses

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.DatePickerLayoutBinding
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentAllExpensesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllExpensesFragment : Fragment() {

    private val viewmodel: AllExpensesViewmodel by viewModels()
    lateinit var binding: LayoutFragmentAllExpensesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutFragmentAllExpensesBinding>(
            inflater,
            R.layout.layout_fragment_all_expenses,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        initFragment()
        setupActionBar()
    }

    private fun initFragment() {
        val adapter = viewmodel.expandableAdapter
        binding.elAllExpenses.setAdapter(adapter)
        viewmodel.loadExpandableData()
    }

    private fun setupActionBar() {
        (activity as BaseActivity).setUpActitionBar(binding.custumActionBar)
        (activity as BaseActivity).setActionBarTitle("All Expenses", Gravity.START)
    }
}