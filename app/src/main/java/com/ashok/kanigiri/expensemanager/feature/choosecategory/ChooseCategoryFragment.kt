package com.ashok.kanigiri.expensemanager.feature.choosecategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentChooseCategoryBinding
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseCategoryFragment: Fragment() {

    lateinit var binding: LayoutFragmentChooseCategoryBinding
    private val viewmodel:ChooseCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_choose_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        setUpRecyclerView()
        observeViewmodel()
        handleNewExpenseCategoryCreated()
    }

    private fun handleNewExpenseCategoryCreated() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(AppConstants.SEND_CREATED_EXPENSE_KEY)?.observe(viewLifecycleOwner, Observer {
            Log.d("kndwknd", "HHH: $it")
        })
    }

    private fun observeViewmodel() {
        viewmodel.event.observe(viewLifecycleOwner, Observer { event->
            when(event){
                is ChooseCategoryViewmodelEvent.OpenCreateExpenseDialog->{
                   findNavController().navigate(
                       ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToExpenseCategoryDialogFragment(false)
                   )
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        binding.rvChooseCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        viewmodel.submitAddAdapterData()
        viewmodel.submitItemAdapterData(viewmodel.getDefaultCategorysList())
    }
}