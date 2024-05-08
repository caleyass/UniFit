package com.unifit.unifit.presentation.ui.fragments.fitness

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.unifit.unifit.R
import com.unifit.unifit.databinding.FragmentFitnessWorkoutEndBinding
import com.unifit.unifit.presentation.ui.customs.AlertDialogEnterWeightHeight
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper
import com.unifit.unifit.presentation.viewmodels.FitnessProgramExerciseViewModel
import com.unifit.unifit.presentation.viewmodels.FitnessWorkoutEndViewModel
import com.unifit.unifit.presentation.viewmodels.FitnessWorkoutEndViewModelFactory

//TODO MAKE PROGRESS BAR PRETTIER
class FitnessWorkoutEndFragment : Fragment(){
    private var binding : FragmentFitnessWorkoutEndBinding? = null

    private val sharedPreferences by lazy { requireContext().getSharedPreferences("user_data", 0) }

    private val sharedViewModel : FitnessProgramExerciseViewModel by activityViewModels()
    private val fitnessWorkoutEndViewModel : FitnessWorkoutEndViewModel by activityViewModels{
        FitnessWorkoutEndViewModelFactory(sharedPreferences)
    }

    private val nestedScrollView by lazy { binding?.nestedScrollView }
    private val progressBar by lazy { nestedScrollView?.findViewById<ProgressBar>(R.id.progressBarBMI) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFitnessWorkoutEndBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        addAlertDialog()
        binding?.appBarLayout?.let { EdgeToEdgeHelper.updateMarginToStatusBarInsets(it) }

        binding?.header?.findViewById<TextView>(R.id.tvExercisesNumber)?.text = sharedViewModel.counter.toString()
        binding?.header?.findViewById<TextView>(R.id.tvDurationNumber)?.text = sharedViewModel.getTimeFormatted()
        binding?.header?.findViewById<TextView>(R.id.tvKCalNumber)?.text = sharedViewModel.getKCalFormatted(fitnessWorkoutEndViewModel.weight.value ?: 70)

    }

    private fun addAlertDialog(){
        //TODO Add alert dialog higher then keyboard
        nestedScrollView?.findViewById<CardView>(R.id.cvWeightHeight)?.setOnClickListener {
            //open alert dialog
            val alertDialog = AlertDialogEnterWeightHeight.create(this.requireContext()) { weight, height ->
                fitnessWorkoutEndViewModel.saveWeightHeight(
                    weight,
                    height
                )
            }
            alertDialog.show()
        }
    }

    private fun addObservers() {
        observeWeight()
        observeHeight()
        observeBMI()
    }

    private fun observeWeight(){
        fitnessWorkoutEndViewModel.weight.observe(viewLifecycleOwner) {
            Log.d("TAG", "Weight changed: $it")
            it?.let {
                nestedScrollView?.findViewById<TextView>(R.id.tvWeightValue)?.text =
                    it.toString()
            }
        }
    }

    private fun observeHeight(){
        fitnessWorkoutEndViewModel.height.observe(viewLifecycleOwner) {
            Log.d("TAG", "Height changed: $it")
            it?.let {
                nestedScrollView?.findViewById<TextView>(R.id.tvHeightValue)?.text =
                    it.toString()
            }
        }
    }

    private fun observeBMI(){
        fitnessWorkoutEndViewModel.bmi.observe(viewLifecycleOwner) {
            it?.let{
                nestedScrollView?.findViewById<TextView>(R.id.tvBMIValue)?.text = fitnessWorkoutEndViewModel.getBMIinString()
                progressBar?.progress = it.toInt()
                fitnessWorkoutEndViewModel.getColourOfProgressBar()?.let { color ->
                    progressBar?.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), color))
                }
                return@observe
            }
            nestedScrollView?.findViewById<TextView>(R.id.tvBMIValue)?.text = getString(R.string.enter_weight_height)
            progressBar?.visibility = View.INVISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}