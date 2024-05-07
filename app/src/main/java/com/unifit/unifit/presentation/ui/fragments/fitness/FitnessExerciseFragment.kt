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
import com.unifit.unifit.data.utils.Resource
import com.unifit.unifit.databinding.FragmentFitnessExerciseBinding
import com.unifit.unifit.presentation.ui.customs.ProgressDrawable
import com.unifit.unifit.presentation.viewmodels.FitnessProgramExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

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
        sharedViewModel.nameOfWorkoutPart = "Warm-up"
        val progressDrawable = ProgressDrawable(sharedViewModel.index, this.requireContext())
        binding?.progressBar?.progressDrawable = progressDrawable
        CoroutineScope(Dispatchers.IO).launch {
            bindFitnessExerciseToUI()
            sharedViewModel.getNextFitnessProgramExercise()?.collect { resource ->
                when(resource){
                    is Resource.Error -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            end = true
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private suspend fun bindFitnessExerciseToUI(){
        sharedViewModel.getCurrentFitnessProgramExercise()?.collect { fitnessExercise ->
            withContext(Dispatchers.Main) {
                binding?.gifImageView?.let {
                    Glide.with(requireContext())
                        .asGif()
                        .load(fitnessExercise.data?.gif)
                        .into(it)
                }
                binding?.tvExerciseName?.text = fitnessExercise.data?.name
                bindCountDownTimer(fitnessExercise.data?.time.toString().toLong()  * 1000)
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
                Log.d("TAG", "onTick: ${sharedViewModel.workoutTime}T")
            }

            override fun onFinish() {
                if(end)
                    navigateToEnd()
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