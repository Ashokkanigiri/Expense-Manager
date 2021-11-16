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
        observeViewmodel()
    }

    private fun observeViewmodel() {
        viewmodel.getAllExpenses().observe(viewLifecycleOwner, Observer {
            viewmodel.adapter.submitList(it)
        })
        viewmodel.event.observe(viewLifecycleOwner, Observer {it->
            when(it){
                is AllExpensesViewmodelEvent.ShowCalenderDialog->{
                    showCalenderDialog()
                }
                is AllExpensesViewmodelEvent.LoadExpenses->{
                    viewmodel.adapter.submitList(it.data)
                }
            }
        })
    }

    private fun initFragment() {
        val adapter = viewmodel.adapter
        if (!adapter.hasObservers()) adapter.setHasStableIds(true)
        binding.rvAllExpenses.adapter = adapter
    }

    private fun setupActionBar() {
        (activity as BaseActivity).setUpActitionBar(binding.custumActionBar)
        (activity as BaseActivity).setActionBarTitle("All Expenses", Gravity.START)
        (activity as BaseActivity).setTrailingIcon(resources.getDrawable(R.drawable.ic_baseline_filter_alt_24))
        (activity as BaseActivity).setTrailingIcon2(resources.getDrawable(R.drawable.ic_baseline_sort_24))
        (activity as BaseActivity).setTrailingIcon3(resources.getDrawable(R.drawable.ic_baseline_refresh_24))
        (activity as BaseActivity).handleTrailingIconClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showPopup(
                    R.menu.menu_filter_drop_down,
                    (activity as BaseActivity).actionBar.ivTrailingIcon
                )
            }
        })

        (activity as BaseActivity).handleTrailingIcon2ClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showPopup(
                    R.menu.menu_sort_drop_down,
                    (activity as BaseActivity).actionBar.ivTrailing2
                )
            }
        })
        (activity as BaseActivity).handleTrailingIcon3ClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                observeViewmodel()
            }
        })

    }

    fun showCalenderDialog() {
        val dialog = Dialog(requireContext(), R.style.calender_dialog)
        val binding: DatePickerLayoutBinding = DatePickerLayoutBinding.inflate(LayoutInflater.from(context))

        binding.dpFrom.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            binding.fromDate = "${ dayOfMonth }-${ monthOfYear }-${ year }"
        }
        binding.dpTo.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            binding.toDate = "${ dayOfMonth }-${ monthOfYear }-${ year }"
        }
        binding.btnFilter.setOnClickListener {
            viewmodel.fromDate =binding.fromDate
            viewmodel.toDate = binding.toDate
            if(viewmodel.fromDate != null && viewmodel.toDate != null) {
                viewmodel.getAllExpensesForFilteredDate(viewmodel.fromDate?:"", viewmodel.toDate?:"")
            }
            dialog.dismiss()
        }
        binding.ivBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog.setContentView(binding.getRoot())
        dialog.show()
    }

    private fun showPopup(menu: Int, view: View) {
        val popupMenu =
            PopupMenu(requireContext(), view)
        popupMenu.menuInflater?.inflate(menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.menu_filter_date -> viewmodel.filterExpensesByDate()
                    R.id.menu_filter_categorys -> viewmodel.filterExpensesByCategorys()
                    R.id.menu_sort_hl -> viewmodel.sortExpensesHightoLow()
                    R.id.menu_sort_lh -> viewmodel.sortExpensesLowToHigh()
                    R.id.menu_sort_recent -> viewmodel.sortRecentExpenses()
                }
                return true
            }
        })
        popupMenu.show()
    }
}