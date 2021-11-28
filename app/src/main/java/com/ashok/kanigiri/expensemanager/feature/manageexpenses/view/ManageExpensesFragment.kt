package com.ashok.kanigiri.expensemanager.feature.manageexpenses.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentManageExpensesBinding
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewmodel.ManageExpensesViewModel
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewmodel.ManageExpensesViewmodelEvent
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@AndroidEntryPoint
class ManageExpensesFragment : Fragment() {

    lateinit var binding: LayoutFragmentManageExpensesBinding

    private val viewmodel: ManageExpensesViewModel by viewModels()

    @Inject
    lateinit var roomRepository: RoomRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_fragment_manage_expenses,
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
        observeViewModel()
        setupActionBar()
    }

    private fun initFragment() {
        val adapter = viewmodel.setAdapter()
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true)
        }
        binding.rvManageExpenses.adapter = adapter
    }

    private fun observeViewModel() {
        roomRepository.getCategoryDao().getAllCategorysByExpenseMonth(viewmodel.getCurrentExpenseMonth()?.expenseMonthId?:1).asLiveData(Dispatchers.Main)
            .observe(viewLifecycleOwner, Observer {
                viewmodel.loadAdapter(it)
            })
        viewmodel.createNewExpense.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(
                ManageExpensesFragmentDirections.actionAddExpenseFragmentToCreateExpenseDialogFragment(
                    categoryId = it.expenseCategoryId,
                    categoryName = it.expenseCategoryName
                )
            )
        })
        viewmodel.navigateToExpenseList.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(
                ManageExpensesFragmentDirections.actionAddExpenseFragmentToExpenseListFragment(
                    it.expenseCategoryId,
                    it.expenseCategoryName
                )
            )
        })
        viewmodel.event.observe(viewLifecycleOwner, Observer { event->
            when(event){
                is ManageExpensesViewmodelEvent.OnEditExpenseCategoryClicked ->{
                    this.findNavController().navigate(
                        ManageExpensesFragmentDirections.actionAddExpenseFragmentToEditCategoryBottomSheet(event.expenseCategoryId)
                    )
                }
            }
        })
    }

    private fun setupActionBar() {
        (activity as BaseActivity).setUpActitionBar(binding.custumActionBar)
        (activity as BaseActivity).setActionBarTitle("My Expense List", Gravity.START)
        (activity as BaseActivity).enableTrailingIconVisibility()
        (activity as BaseActivity).handleTrailingIconClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                findNavController().navigate(
                    ManageExpensesFragmentDirections.actionAddExpenseFragmentToExpenseCategoryDialogFragment2(
                        true
                    )
                )
            }
        })
    }

}