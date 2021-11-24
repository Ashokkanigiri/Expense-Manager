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
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutHomeFragmentBinding
import com.ashok.kanigiri.expensemanager.feature.home.viewmodel.HomeViewModel
import com.ashok.kanigiri.expensemanager.feature.home.viewmodel.HomeViewModelEvent
import com.ashok.kanigiri.expensemanager.feature.welcome.WelcomeActivity
import com.ashok.kanigiri.expensemanager.service.ExpenseAppDB
import com.ashok.kanigiri.expensemanager.service.SharedPreferenceService
import com.ashok.kanigiri.expensemanager.service.room.repository.RoomRepository
import com.ashok.kanigiri.expensemanager.utils.AppConstants
import com.ashok.kanigiri.expensemanager.utils.AppUtils
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    }

    private fun observeViewModel() {
        viewmodel.roomRepository.getExpenseMonthDao().getLatestExpenseMonthFlow().asLiveData(Dispatchers.Main).observe(viewLifecycleOwner, Observer {
            binding.expenseMonth = it
        })

        viewmodel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
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

    private fun setUpExpenditureGraph() {
        binding.expenditureChart.setUsePercentValues(true)
        binding.expenditureChart.description.isEnabled = true
        binding.expenditureChart.isDragDecelerationEnabled = false
        binding.expenditureChart.dragDecelerationFrictionCoef = 0.95f
        binding.expenditureChart.isDrawHoleEnabled = false
        binding.expenditureChart.setHoleColor(Color.WHITE)
        binding.expenditureChart.transparentCircleRadius = 40f
        binding.expenditureChart.isRotationEnabled = false
        var freeHandMoney: Double = 0.0
        val yValues = ArrayList<PieEntry>()
        viewmodel.getListOfSelectedCategorysForExpenseMonth().observe(viewLifecycleOwner, { list ->

            list?.forEach {
                if (it.expensePrice > 0.0) {
                    binding.expenditureChart.clear()
                    freeHandMoney =
                        (viewmodel.getCurrentMonthSalary()) - (list.map { it -> it.expensePrice }
                            .sum())
                    val percent: Float =
                        ((it.expensePrice.toFloat()) / ((SharedPreferenceService.getUserLoginModel(
                            requireContext()
                        )?.salary?.toFloat()) ?: 0f))
                    yValues.add(
                        PieEntry(
                            percent,
                            viewmodel.getcategoryNameForId(it.expenseCategoryId)
                        )
                    )

                }
            }

            val freeHandPercent: Float =
                freeHandMoney.toFloat() / (SharedPreferenceService.getUserLoginModel(requireContext())?.salary?.toFloat()
                    ?: 0f)
            yValues.add(PieEntry(freeHandPercent, "Free Hand Money"))
            val pieDataSet = PieDataSet(yValues, "")
            pieDataSet.sliceSpace = 1.5f
            pieDataSet.selectionShift = 5f
            pieDataSet.setColors(
                AppConstants.graphColours.toIntArray(),
                200
            )
            val pieData = PieData(pieDataSet)
            pieData.setValueTextSize(10f)
            pieData.setValueTextColor(Color.WHITE)

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