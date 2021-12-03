package com.ashok.kanigiri.expensemanager.feature.choosecategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentChooseCategoryBinding
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.entity.ExpenseCategory
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import com.google.android.material.snackbar.Snackbar
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
        viewmodel.injectDefaultCategorysList()
        handleNewExpenseCategoryCreated()
    }

    private fun handleNewExpenseCategoryCreated() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(AppConstants.SEND_CREATED_EXPENSE_KEY)?.observe(viewLifecycleOwner, Observer {
            viewmodel.insertNewCategory(it)
        })
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findNavController().currentBackStackEntry?.savedStateHandle?.remove<String>(AppConstants.SEND_CREATED_EXPENSE_KEY)
    }

    private fun observeViewmodel() {
        viewmodel.event.observe(viewLifecycleOwner, Observer { event->
            when(event){
                is ChooseCategoryViewmodelEvent.OpenCreateExpenseDialog->{
                   findNavController().navigate(
                       ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToExpenseCategoryDialogFragment(false)
                   )
                }
                is ChooseCategoryViewmodelEvent.NavigateToEditExpenses->{
                    findNavController().navigate(
                        ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToEditExpenseFragment()
                    )
                    SharedPreferenceService.putBoolean(SharedPreferenceService.IS_USER_CHOOSED_CATEGORYS, true, requireContext())

                }
                is ChooseCategoryViewmodelEvent.HandleCancelButtonClicked->{
                    requireActivity().finish()
                }
                is ChooseCategoryViewmodelEvent.ShowSnackBar->{
                    showErrorSnackBar()
                }
            }
        })

        viewmodel.roomRepository.getCategoryDao().getAllExpenseCategorys().asLiveData().observe(viewLifecycleOwner, Observer {
            Log.d("wflwfmwlfm", "FRAGMENT :: DATA:: ${it?.size?:0}")
            viewmodel.selectedCategorys = it
            viewmodel.submitItemAdapterData(it)
        })
    }

    private fun showErrorSnackBar() {
        Snackbar.make(
            requireView(),
            "Please select atlease three categorys to proceed furthur",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setUpRecyclerView() {
        binding.rvChooseCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        viewmodel.submitAddAdapterData()
    }
}