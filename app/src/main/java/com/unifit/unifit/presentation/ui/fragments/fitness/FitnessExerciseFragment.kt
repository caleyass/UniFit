package com.unifit.unifit.presentation.ui.fragments.fitness

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.unifit.unifit.databinding.FragmentFitnessExerciseBinding
import com.unifit.unifit.presentation.viewmodel.FitnessProgramExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FitnessExerciseFragment : Fragment() {
    private var binding : FragmentFitnessExerciseBinding? = null

    private val viewModel : FitnessProgramExerciseViewModel by viewModels()

    //@Inject lateinit var firebaseApi: FirebaseApi

    private var countDownTimer: CountDownTimer? = null
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
            bindFitnessExerciseToUI()
        }
    }

    private suspend fun bindFitnessExerciseToUI(){
        viewModel.getFitnessProgramExercise(
            category = "Abdomen",
            nameOfWorkoutPart = "Warm-up",
            index = 0
        ).collect { fitnessExercise ->
            withContext(Dispatchers.Main) {
                binding?.gifImageView?.let {
                    Glide.with(requireContext())
                        .asGif()
                        .load(fitnessExercise?.gif)
                        .into(it)
                }
                binding?.tvExerciseName?.text = fitnessExercise?.name
                bindCountDownTimer(fitnessExercise?.time.toString().toLong()  * 1000)
            }
        }
    }

    private fun bindCountDownTimer(startMilis:Long){
        countDownTimer = object : CountDownTimer(
            startMilis,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.tvTime?.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {

            }
        }.start()
    }

    private fun navigateToRest(){

    }

}