package com.ashok.kanigiri.expensemanager.feature.createnewaccount

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentCreateAccountBinding
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.ticker

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    lateinit var binding: LayoutFragmentCreateAccountBinding
    private val viewmodel: CreateAccountViewmodel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_fragment_create_account,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.data = viewmodel.userLoginModel
        binding.viewmodel = viewmodel
        binding.fragment = this
        handleBackPressed()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewmodel.event.observe(viewLifecycleOwner, Observer {event->
            when(event){
                is CreateAccountViewmodelEvent.NavigateToLoginScreen ->{
                    findNavController().popBackStack()
                }
            }
        })
    }

    fun openDatePickerDialog(){
        AppUtils.getSelectedDateFromDatePicker(requireContext())
            .observe(viewLifecycleOwner, Observer {
                viewmodel.saveDateOfBirthFromPicker(it)
            })
    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if(isAdded) findNavController().popBackStack()
        }
    }
}

