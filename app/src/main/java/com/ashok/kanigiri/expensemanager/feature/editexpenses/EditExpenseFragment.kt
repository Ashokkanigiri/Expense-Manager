package com.ashok.kanigiri.expensemanager.feature.editexpenses

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ashok.kanigiri.expensemanager.MainActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentEditExpensesBinding
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class EditExpenseFragment: Fragment() {

    private val viewmodel: EditExpensesViewmodel by viewModels()
    lateinit var binding: LayoutFragmentEditExpensesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_edit_expenses, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        observeViewmodel()
        handleBackPresed()
        handleRecyclerViewAnimations()
    }

    private fun handleRecyclerViewAnimations() {
        val adapter = viewmodel.setAdapter()
        adapter.setHasStableIds(true)
        binding.editExpenses.adapter = adapter
    }

    private fun handleBackPresed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }
    }

    private fun observeViewmodel() {
        viewmodel.getExpenseList().observe(viewLifecycleOwner, Observer {
            viewmodel.setAdapter().submitList(it)
        })
        viewmodel.event.observe(viewLifecycleOwner, Observer { event->
            when(event){
                is EditExpensesViewModelEvent.NavigateToMainActivity->{
                    SharedPreferenceService.putBoolean(SharedPreferenceService.IS_USER_EDITED_CATEGORYS, true, requireContext())
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }
                is EditExpensesViewModelEvent.ShowSalaryLimitReachedSnackbar ->{
                    showSnackBar()
                }
                is EditExpensesViewModelEvent.HandleCancelButtonClicked -> {
                    requireActivity().finish()
                }
            }
        })
    }

    private fun showSnackBar() {
        Snackbar.make(requireView(), "Expenses limit reached: Please Manage your Expenses According to your salary", Snackbar.LENGTH_SHORT).show()
    }
}