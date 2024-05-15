package com.unifit.unifit.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.unifit.unifit.R
import com.unifit.unifit.databinding.FragmentStarterBinding
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper


//TODO ADD TABBAR WITH 4 ICONS
class StarterFragment : Fragment() {
    private var binding : FragmentStarterBinding? = null
    private var barChart : BarChart? = null
    private var barChart2 : BarChart? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStarterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.linearLayoutButtons?.let {
            EdgeToEdgeHelper.updateMarginToSystemBarsInsets(it)
        }

        binding?.btnSettings?.let {
            EdgeToEdgeHelper.updateMarginToStatusBarInsets(it)
        }
        binding?.btnFitness?.setOnClickListener {
            onFitnessProgramClicked()
        }
        binding?.btnSettings?.setOnClickListener {
            onSettingsClicked()
        }

        barChart = binding?.barChart1
        barChart2 = binding?.barChart2

        //val barDataSet = BarDataSet(entries, title)
        showBarChart()
        showBarChart2()
    }

    private fun showBarChart() {
        val valueList = ArrayList<Double>()
        val entries = ArrayList<BarEntry>()
        val title = "Burned calories"

        //input data
        //valueList.add(50.0)

        for (i in 0..5) {
            valueList.add(i * 100.1)
        }

        //fit the data into a bar
        for (i in valueList.indices) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }
        val barDataSet = BarDataSet(entries, title)
        barDataSet.setColor(ContextCompat.getColor(this.requireContext(), R.color.green_deep))
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
        initBarChart()
        val data = BarData(barDataSet)
        barChart!!.data = data
        barChart!!.description.isEnabled = false;
        barChart!!.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.shape_transparent_white)
        barChart!!.invalidate()
    }

    private fun initBarChart() {
        //hiding the grey background of the chart, default false if not set
        barChart!!.setDrawGridBackground(false)
        //remove the bar shadow, default false if not set
        barChart!!.setDrawBarShadow(false)
        //remove border of the chart, default false if not set
        barChart!!.setDrawBorders(false)

        //remove the description label text located at the lower right corner
        val description = Description()
        description.setEnabled(false)
        barChart!!.description = description

        val xAxis = barChart!!.xAxis
        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)
        val dates: List<String> = listOf("10.04", "11.04", "12.04", "13.04", "14.04", "15.04")

        val xAxisFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // Convert the float value to an integer index
                val index = value.toInt()
                // Check if the index is within the bounds of your dates list
                return if (index >= 0 && index < dates.size) {
                    // If yes, return the corresponding date from the list
                    dates[index]
                } else {
                    // If not, return an empty string or any default label
                    ""
                }
            }
        }

        xAxis.valueFormatter =xAxisFormatter
        val leftAxis = barChart!!.axisLeft
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawLabels(false)
        val rightAxis = barChart!!.axisRight
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false)
        val legend = barChart!!.legend
        //setting the shape of the legend form to line, default square shape
        legend.form = Legend.LegendForm.LINE
        //setting the text size of the legend
        legend.textSize = 11f
        //setting the alignment of legend toward the chart
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        //setting the stacking direction of legend
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false)
    }

    private fun showBarChart2() {
        val valueList = ArrayList<Double>()
        val entries = ArrayList<BarEntry>()
        val title = "Hours of sleep"

        //input data
        //valueList.add(50.0)
        valueList.add(5.5)
        valueList.add(6.5)
        valueList.add(7.3)
        valueList.add(8.0)


        /*for (i in 0..5) {
            valueList.add(i * 100.1)
        }*/

        //fit the data into a bar
        for (i in valueList.indices) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }
        val barDataSet = BarDataSet(entries, title)
        barDataSet.setColor(ContextCompat.getColor(this.requireContext(), R.color.green_deep))
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
        initBarChart2()
        val data = BarData(barDataSet)
        barChart2!!.data = data
        barChart2!!.description.isEnabled = false;
        barChart2!!.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.shape_transparent_white)
        barChart2!!.invalidate()
    }

    private fun initBarChart2() {
        //hiding the grey background of the chart, default false if not set
        barChart2!!.setDrawGridBackground(false)
        //remove the bar shadow, default false if not set
        barChart2!!.setDrawBarShadow(false)
        //remove border of the chart, default false if not set
        barChart2!!.setDrawBorders(false)

        //remove the description label text located at the lower right corner
        val description = Description()
        description.setEnabled(false)
        barChart2!!.description = description

        val xAxis = barChart2!!.xAxis
        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)

        // Assuming you have a list of dates as String or any date format
        val dates: List<String> = listOf("10.04", "11.04", "12.04", "13.04")

        val xAxisFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // Convert the float value to an integer index
                val index = value.toInt()
                // Check if the index is within the bounds of your dates list
                return if (index >= 0 && index < dates.size) {
                    // If yes, return the corresponding date from the list
                    dates[index]
                } else {
                    // If not, return an empty string or any default label
                    ""
                }
            }
        }

        xAxis.valueFormatter =xAxisFormatter
        val leftAxis = barChart2!!.axisLeft
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawLabels(false)
        leftAxis.axisMinimum = 0f
        val rightAxis = barChart2!!.axisRight
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false)
        rightAxis.axisMinimum = 0f
        val legend = barChart2!!.legend
        //setting the shape of the legend form to line, default square shape
        legend.form = Legend.LegendForm.LINE
        //setting the text size of the legend
        legend.textSize = 11f
        //setting the alignment of legend toward the chart
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        //setting the stacking direction of legend
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false)
    }


    private fun onFitnessProgramClicked() {
        val action = StarterFragmentDirections.actionStarterFragmentToFitnessFragment()
        findNavController().navigate(action)
    }

    private fun onSettingsClicked() {
        val action = StarterFragmentDirections.actionStarterFragmentToSettingsFragment()
        findNavController().navigate(action)
    }
}