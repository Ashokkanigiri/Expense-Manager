package com.ashok.kanigiri.expensemanager.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutFragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    lateinit var binding: LayoutFragmentLoginBinding
    private val viewmodel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        observeViewModel()
    }

    private fun observeViewModel() {
        viewmodel.event.observe(viewLifecycleOwner, Observer { event ->
            when(event){
                is LoginViewModelEvent.NavigateToCreateAccountPage ->{
                    navigateToCreateAccountScreen()
                }
            }
        })
    }

    private fun navigateToCreateAccountScreen() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment()
        )
    }
}