package com.unifit.unifit.presentation.ui.fragments.fitness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.unifit.unifit.databinding.FragmentFitnessWorkoutBinding
import com.unifit.unifit.presentation.viewmodels.FitnessProgramExerciseViewModel

class FitnessWorkoutFragment : Fragment() {

    private var binding : FragmentFitnessWorkoutBinding? = null

    private val sharedViewModel : FitnessProgramExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFitnessWorkoutBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.nameOfWorkout?.text = "${sharedViewModel.nameOfWorkout}"
        binding?.buttonStart?.setOnClickListener {
            val action = FitnessWorkoutFragmentDirections.actionFitnessWorkoutFragmentToFitnessExercisesFragment()
            findNavController().navigate(action)
        }
    }
}