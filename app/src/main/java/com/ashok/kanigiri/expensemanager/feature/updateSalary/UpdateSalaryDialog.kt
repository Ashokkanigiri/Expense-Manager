package com.ashok.kanigiri.expensemanager.feature.updateSalary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutDialogUpdateSalaryBinding
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentCreateExpenseBinding
import com.ashok.kanigiri.expensemanager.feature.createexpensedialog.viewmodel.CreateExpenseDialogViewModel
import com.ashok.kanigiri.expensemanager.feature.createexpensedialog.viewmodel.CreateExpenseDialogViewmodelEvent
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.ashok.kanigiri.expensemanager.utils.DateUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateSalaryDialog: BottomSheetDialogFragment() {

    lateinit var binding: LayoutDialogUpdateSalaryBinding
    private val viewModel: UpdateSalaryDialogViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_dialog_update_salary,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.event.observe(viewLifecycleOwner, Observer { event->
            when(event){
                is UpdateSalaryDialogViewmodelEvent.UpdateSalary->{
                    updateCurrentSalary()
                }
                is UpdateSalaryDialogViewmodelEvent.DismissDialog->{
                    dialog?.dismiss()
                }
            }
        })
    }

    private fun updateCurrentSalary() {
        val enteredSalary = binding.etSalary.text?.trim()?.toString()
        if(enteredSalary != null && enteredSalary != ""){
            viewModel.updateCurrentSalary(enteredSalary.toDouble())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}