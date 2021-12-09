package com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.view

import android.content.DialogInterface
import android.os.Bundle
import android.os.Trace
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentCreateNewExpenseCategoryBinding
import com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.viewmodel.ExpenseCategoryDialogViewModel
import com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.viewmodel.ExpenseCategoryDialogViewmodelEvent
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExpenseCategoryDialogFragment: BottomSheetDialogFragment() {

    var _binding: LayoutFragmentCreateNewExpenseCategoryBinding? = null
    val binding: LayoutFragmentCreateNewExpenseCategoryBinding get() = _binding!!

    private val dialogViewModel: ExpenseCategoryDialogViewModel by viewModels()

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
        _binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_create_new_expense_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = dialogViewModel
        dialogViewModel.populateReserveCash()
        loadArguments()
        observeViewModel()
    }

    private fun loadArguments() {
        arguments?.getBoolean(AppConstants.SHOULD_SHOW_EXPENSE_PRICE)?.let {
            dialogViewModel.shouldShowExpensePrice = it
        }
    }

    private fun observeViewModel() {
        dialogViewModel.event.observe(viewLifecycleOwner, Observer {
            when(it){
                ExpenseCategoryDialogViewmodelEvent.DismissDialog ->{
                    dialog?.dismiss()
                }
                is ExpenseCategoryDialogViewmodelEvent.PopulateReserveCAsh ->{
                    binding.reserveCash = it.reserveCash
                }
                is ExpenseCategoryDialogViewmodelEvent.SendCreatedCAtegoryNAme ->{
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(AppConstants.SEND_CREATED_EXPENSE_KEY, it.name)
                    dialog?.dismiss()
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogViewModel.event.removeObservers(viewLifecycleOwner)
        findNavController().previousBackStackEntry?.savedStateHandle?.remove<String>(AppConstants.SEND_CREATED_EXPENSE_KEY)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }
}