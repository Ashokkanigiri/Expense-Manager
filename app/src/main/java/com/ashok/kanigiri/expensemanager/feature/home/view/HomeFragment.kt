package com.ashok.kanigiri.expensemanager.feature.home.view

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ashok.kanigiri.expensemanager.BaseActivity
import com.ashok.kanigiri.expensemanager.R
import com.ashok.kanigiri.expensemanager.databinding.LayoutHomeFragmentBinding
import com.ashok.kanigiri.expensemanager.feature.home.viewmodel.HomeViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: LayoutHomeFragmentBinding
    private val viewmodel: HomeViewModel by viewModels()

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
        setupActionBar()
        setUpExpenditureGraph()
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

        yValues.add(PieEntry(34f, "PartyA"))
        yValues.add(PieEntry(23f, "PartyB"))
        yValues.add(PieEntry(14f, "PartyC"))
        yValues.add(PieEntry(35f, "PartyD"))
        yValues.add(PieEntry(40f, "PartyE"))
        yValues.add(PieEntry(23f, "PartyF"))

        val pieDataSet = PieDataSet(yValues, "Demo")
        pieDataSet.sliceSpace = 1.5f
        pieDataSet.selectionShift = 5f
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS+ColorTemplate.MATERIAL_COLORS+ColorTemplate.PASTEL_COLORS, 200)


        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(10f)
        pieData.setValueTextColor(Color.BLACK)

        binding.expenditureChart.data = pieData
        binding.expenditureChart.animateY(1500, Easing.EaseInOutCubic)
        val desc = Description()
        desc.isEnabled = false
        binding.expenditureChart.description = desc
    }

    private fun setupActionBar() {
        (activity as BaseActivity).setUpActitionBar(binding.custumActionBar)
        (activity as BaseActivity).setActionBarTitle("Home", Gravity.START)
    }
}