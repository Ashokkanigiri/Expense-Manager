package com.ashok.kanigiri.expensemanager.feature.editcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutEditCategoryBottomsheetBinding
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentCreateExpenseBinding
import com.ashok.kanigiri.expensemanager.feature.createexpensedialog.viewmodel.CreateExpenseDialogViewModel
import com.ashok.kanigiri.expensemanager.feature.createexpensedialog.viewmodel.CreateExpenseDialogViewmodelEvent
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.ashok.kanigiri.expensemanager.utils.DateUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCategoryBottomSheet: BottomSheetDialogFragment() {


    lateinit var binding: LayoutEditCategoryBottomsheetBinding

    private val viewmodel: EditCategoryBottomSheetViewModel by viewModels()

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
            R.layout.layout_edit_category_bottomsheet,
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
        loadBottomSheet()
        observeViewmodel()
    }

    private fun loadBottomSheet() {
        viewmodel.getCategoryDetails()?.let {
            viewmodel.categoryNameObserver.set(it.expenseCategoryName)
            viewmodel.categoryTargetPriceObserver.set("${it.expenseCategoryTargetPrice}")
        }
    }

    private fun loadArguments() {
        viewmodel.categoryId = arguments?.getInt("categoryId")?:0
    }

    private fun observeViewmodel() {
        viewmodel.event.observe(viewLifecycleOwner, Observer { event->
            when(event){
                is EditCategoryBottomSheetViewModelEvent.OnEditButtonClicked->{
                    viewmodel.updateCategoryDetails()
                }
                is EditCategoryBottomSheetViewModelEvent.DismissDialog ->{
                    dialog?.dismiss()
                }
                is EditCategoryBottomSheetViewModelEvent.ShowErrorDialog -> {
                    binding.editTextNumber.setError("Target amount should not exceed Rs. ${viewmodel.getMaximumTargetPriceForCategory()}")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        AppUtils.getSelectedDateFromDatePicker(null).removeObservers(viewLifecycleOwner)
    }
}