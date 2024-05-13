package com.unifit.unifit.presentation.ui.fragments.fitness

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.unifit.unifit.databinding.FragmentFitnessProgramBinding
import com.unifit.unifit.presentation.adapter.FitnessWorkoutAdapter
import com.unifit.unifit.presentation.viewmodels.FitnessProgramExerciseViewModel
import com.unifit.unifit.presentation.viewmodels.FitnessWorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FitnessProgramFragment : Fragment() {

    private var binding : FragmentFitnessProgramBinding? = null
    @Inject lateinit var factory: FitnessWorkoutViewModel.FitnessWorkoutFactory

    private val viewModel : FitnessWorkoutViewModel by viewModels{
        FitnessWorkoutViewModel.provideFitnessWorkoutViewModelFactory(factory, getCategoryName())
    }

    private val sharedViewModel : FitnessProgramExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFitnessProgramBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecylerView()
    }

    private fun initializeRecylerView() {
        val adapter = FitnessWorkoutAdapter(::onFitnessProgramClicked)
        binding?.recyclerView?.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowFitnessWorkouts.collect {
                adapter.submitData(it)
            }
        }
    }

    private fun getCategoryName():String{
        return sharedViewModel.category!!
    }

    private fun onFitnessProgramClicked(categoryName:String) {
        Log.d("FragmentNavigation", "onFitnessProgramClicked: $categoryName")
        sharedViewModel.nameOfWorkout = categoryName
        val action = FitnessProgramFragmentDirections.actionFitnessProgramFragmentToFitnessWorkoutFragment()
        findNavController().navigate(action)
    }

}