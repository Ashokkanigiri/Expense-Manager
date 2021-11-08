package com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.view

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentCreateNewExpenseCategoryBinding
import com.ashok.kanigiri.expensemanager.feature.expensecategorydialog.viewmodel.ExpenseCategoryDialogViewModel
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseTypes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExpenseCategoryDialogFragment: BottomSheetDialogFragment() {

    var _binding: LayoutFragmentCreateNewExpenseCategoryBinding? = null
    val binding: LayoutFragmentCreateNewExpenseCategoryBinding get() = _binding!!

    private val dialogViewModel: ExpenseCategoryDialogViewModel by viewModels()

    companion object{
         val INSTANCE = ExpenseCategoryDialogFragment()
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
        _binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_create_new_expense_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = dialogViewModel
        setUpCategorySpinner()
        observeViewModel()
    }

    private fun observeViewModel() {
        dialogViewModel.event.observe(viewLifecycleOwner, Observer {
            if(it.peekContent()){
                dialog?.dismiss()
            }
        })
        dialogViewModel.reserveCash()?.let {
            binding.reserveCash = it
        }
    }

    private fun setUpCategorySpinner() {
        val list = ArrayList<String>()
        list.add(ExpenseTypes.FOOD.expenseLitral)
        list.add(ExpenseTypes.GYM.expenseLitral)
        list.add(ExpenseTypes.HOUSE_RENT.expenseLitral)
        list.add(ExpenseTypes.INVESTING.expenseLitral)
        list.add(ExpenseTypes.MEDICAL.expenseLitral)
        list.add(ExpenseTypes.MONTHLY_EMI.expenseLitral)
        list.add(ExpenseTypes.SHOPPING.expenseLitral)
        list.add(ExpenseTypes.TRAVEL.expenseLitral)
        list.add(ExpenseTypes.MISCELLANEOUS.expenseLitral)
        list.add(ExpenseTypes.FREE_HAND_MONEY.expenseLitral)

        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerExpenseCategory.adapter = adapter
        binding.spinnerExpenseCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedPosition: String = (parent?.adapter?.getItem(position) as String)
                dialogViewModel.expenseCategoryName.set(selectedPosition)
                Log.d("sakfn", "Item Selected ${(parent.adapter?.getItem(position))}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("sakfn", "Nothing selected")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ajafjaf", "ON onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ajafjaf", "ON ONDESTROY VIEW()")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d("ajafjaf", "ON onDismiss")
        _binding = null
    }
}