package com.ashok.kanigiri.expensemanager.feature.home.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutHomeFragmentBinding
import com.ashok.kanigiri.expensemanager.feature.home.viewmodel.HomeViewModel
import com.ashok.kanigiri.expensemanager.feature.home.viewmodel.HomeViewModelEvent
import com.ashok.kanigiri.expensemanager.feature.welcome.WelcomeActivity
import com.ashok.kanigiri.expensemanager.service.ExpenseAppDB
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var database: ExpenseAppDB
    lateinit var binding: LayoutHomeFragmentBinding
    private val viewmodel: HomeViewModel by viewModels()

    @Inject
    lateinit var roomRepository: RoomRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_home_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
        observeViewModel()
        setupActionBar()
        setUpExpenditureGraph()
        loadSalaryDetailsFromSharedPrefs()
    }

    private fun observeViewModel() {
        viewmodel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeViewModelEvent.IsSalaryUpdated -> {
                    loadSalaryDetailsFromSharedPrefs()
                }
                is HomeViewModelEvent.Logout -> {
                    logout()
                }
            }
        })
    }

    private fun logout() {
        SharedPreferenceService.clearAllKeys(requireContext())
        startActivity(Intent(requireContext(), WelcomeActivity::class.java))
        database.clearAllTables()
        requireActivity().finish()

    }

    private fun loadSalaryDetailsFromSharedPrefs() {
        binding.salary =
            SharedPreferenceService.getUserLoginModel(requireContext())?.salary?.toInt()
        binding.invalidateAll()
    }

    private fun setUpExpenditureGraph() {
        binding.expenditureChart.setUsePercentValues(true)
        binding.expenditureChart.description.isEnabled = true
        binding.expenditureChart.isDragDecelerationEnabled = false
        binding.expenditureChart.dragDecelerationFrictionCoef = 0.95f
        binding.expenditureChart.isDrawHoleEnabled = false
        binding.expenditureChart.setHoleColor(Color.WHITE)
        binding.expenditureChart.transparentCircleRadius = 40f
        binding.expenditureChart.isRotationEnabled = false

        val yValues = ArrayList<PieEntry>()
        roomRepository.getCategoryDao().getSelectedCategorys().observe(viewLifecycleOwner, Observer { list ->
            list?.forEach {
                if (it.totalUtilizedPrice > 0.0) {
                    binding.expenditureChart.clear()
                    val percent: Float = ((it.totalUtilizedPrice?.toFloat())
                        ?: 0f / SharedPreferenceService.getUserSalary(requireContext()))
                    yValues.add(PieEntry(percent, it.expenseCategoryName))
                }
            }
            val pieDataSet = PieDataSet(yValues, "Demo")
            pieDataSet.sliceSpace = 1.5f
            pieDataSet.selectionShift = 5f
            pieDataSet.setColors(
                ColorTemplate.JOYFUL_COLORS + ColorTemplate.MATERIAL_COLORS + ColorTemplate.PASTEL_COLORS,
                200
            )


            val pieData = PieData(pieDataSet)
            pieData.setValueTextSize(10f)
            pieData.setValueTextColor(Color.BLACK)

            binding.expenditureChart.data = pieData
            binding.expenditureChart.animateY(1500, Easing.EaseInOutCubic)
            val desc = Description()
            desc.isEnabled = false
            binding.expenditureChart.description = desc
        })
    }

    private fun setupActionBar() {
        (activity as BaseActivity).setUpActitionBar(binding.custumActionBar)
        (activity as BaseActivity).setActionBarTitle("Home", Gravity.START)
    }
}