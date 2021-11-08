package com.ashok.kanigiri.expensemanager.feature.createexpensedialog.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentCreateExpenseBinding
import com.ashok.kanigiri.expensemanager.feature.createexpensedialog.viewmodel.CreateExpenseDialogViewModel
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.view.ManageExpensesFragment
import com.ashok.kanigiri.expensemanager.feature.manageexpenses.viewmodel.ManageExpensesViewModel
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateExpenseDialogFragment : BottomSheetDialogFragment() {

    lateinit var binding: LayoutFragmentCreateExpenseBinding

    private val viewmodel: CreateExpenseDialogViewModel by viewModels()

    companion object {
        val INSTANCE = CreateExpenseDialogFragment()
    }

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
            R.layout.layout_fragment_create_expense,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        loadArguments()
        observeViewmodel()
    }

    private fun loadArguments() {
        arguments?.getString(AppConstants.CATEGORY_NAME_KEY)?.let {
        }

        arguments?.getString(AppConstants.CATEGORY_ID_KEY)?.let {
            viewmodel.expenseId = it
        }
    }

    private fun observeViewmodel() {
        viewmodel.showCalenderEvent.observe(viewLifecycleOwner, Observer {
            if (it) {
                AppUtils.getSelectedDateFromDatePicker(requireContext())
                    .observe(viewLifecycleOwner, Observer {
                        binding.editTextDate.text = it
                        viewmodel.selectedDate = it
                    })
            }
        })
        viewmodel.hideDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                dismiss()
            }
        })
        viewmodel.showErrorMsg.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.etExpenseValueField.setError(it)
            }
        })
    }
}