package com.unifit.unifit.presentation.ui.fragments.fitness

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.unifit.unifit.data.utils.Resource
import com.unifit.unifit.databinding.FragmentFitnessRestBinding
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper
import com.unifit.unifit.presentation.viewmodels.FitnessProgramExerciseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FitnessRestFragment : Fragment() {
    private var binding : FragmentFitnessRestBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var secondsRemaining = 30_000L

    private val sharedViewModel : FitnessProgramExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFitnessRestBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnSkip?.let {
            EdgeToEdgeHelper.updateMarginToSystemBarsInsets(it)
        }
        CoroutineScope(Dispatchers.IO).launch {
            sharedViewModel.getCurrentFitnessProgramExercise(increment = false)?.let { fitnessExercise ->
                withContext(Dispatchers.Main) {
                    binding?.tvExerciseName?.text = fitnessExercise.name
                    binding?.gifImageView?.let {
                        Glide.with(requireContext())
                            .asGif()
                            .load(fitnessExercise.gif)
                            .into(it)
                    }
                }
            }
        }
        binding?.btnPause?.setOnClickListener {
            secondsRemaining += 20_000L
            restartCountDownTimer()
        }
        binding?.btnSkip?.setOnClickListener {
            countDownTimer?.cancel()
            val action = FitnessRestFragmentDirections.actionFitnessRestFragmentToFitnessExercisesFragment()
            findNavController().navigate(action)
        }
        countDownTimer = object : CountDownTimer(
            secondsRemaining,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished
                binding?.tvTime?.text = (secondsRemaining / 1000).toString()
            }

            override fun onFinish() {
                goToNextExercise()

            }
        }.start()
    }

    private fun goToNextExercise() {
        val action = FitnessRestFragmentDirections.actionFitnessRestFragmentToFitnessExercisesFragment()
        findNavController().navigate(action)
    }

    private fun restartCountDownTimer() {
        // Cancel the existing timer
        countDownTimer?.cancel()
        // Start a new timer with the updated time
        countDownTimer = object : CountDownTimer(secondsRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished
                updateTimerDisplay()
            }

            override fun onFinish() {
                goToNextExercise()
            }
        }.start()
    }

    private fun updateTimerDisplay() {
        binding?.tvTime?.text = (secondsRemaining / 1000).toString()
    }

}