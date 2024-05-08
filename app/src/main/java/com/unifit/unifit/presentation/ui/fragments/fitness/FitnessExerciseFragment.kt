package com.unifit.unifit.presentation.ui.fragments.fitness

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.unifit.unifit.databinding.FragmentFitnessExerciseBinding
import com.unifit.unifit.presentation.ui.customs.ProgressDrawable
import com.unifit.unifit.presentation.viewmodels.FitnessProgramExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FitnessExerciseFragment : Fragment() {
    private var binding : FragmentFitnessExerciseBinding? = null

    private var countDownTimer: CountDownTimer? = null

    private val sharedViewModel : FitnessProgramExerciseViewModel by activityViewModels()
    private var end = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFitnessExerciseBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {

            sharedViewModel.getFitnessProgramExercises()
            end = sharedViewModel.isLastExercise()

            bindFitnessExerciseToUI()
        }
    }

    private suspend fun bindFitnessExerciseToUI(){
        sharedViewModel.getCurrentFitnessProgramExercise()?.let { fitnessExercise ->
            withContext(Dispatchers.Main) {
                binding?.progressBar?.progressDrawable = ProgressDrawable(sharedViewModel.indexExercise, sharedViewModel.fitnessExercises?.size ?: 3, this@FitnessExerciseFragment.requireContext())
                binding?.gifImageView?.let {
                    Glide.with(requireContext())
                        .asGif()
                        .load(fitnessExercise.gif)
                        .into(it)
                }
                binding?.tvExerciseName?.text = fitnessExercise.name
                bindCountDownTimer(fitnessExercise.time.toString().toLong()  * 1000)
            }
        }
    }

    private fun bindCountDownTimer(startMilis:Long){
        countDownTimer = object : CountDownTimer(
            2000,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.tvTime?.text = (millisUntilFinished / 1000).toString()
                sharedViewModel.workoutTime++
            }

            override fun onFinish() {
                if(end) {
                    if(sharedViewModel.isLastPart()) {
                        navigateToEnd()
                    }
                    else {
                        CoroutineScope(Dispatchers.IO).launch {
                            val f = sharedViewModel.updateFitnessProgramExercises()
                            Log.d("TAG", "${f?.size}")
                            withContext(Dispatchers.Main) {
                                navigateToRest()
                            }
                        }

                    }
                }
                else
                    navigateToRest()
            }
        }.start()
    }

    private fun navigateToRest(){
        val action = FitnessExerciseFragmentDirections.actionFitnessExercisesFragmentToFitnessRestFragment()
        findNavController().navigate(action)
    }

    private fun navigateToEnd(){
        val action = FitnessExerciseFragmentDirections.actionFitnessExercisesFragmentToFitnessWorkoutEndFragment()
        findNavController().navigate(action)
    }

}