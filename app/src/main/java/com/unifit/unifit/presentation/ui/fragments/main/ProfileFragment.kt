package com.unifit.unifit.presentation.ui.fragments.main

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.unifit.unifit.R
import com.unifit.unifit.databinding.FragmentProfileBinding
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper
import com.unifit.unifit.presentation.viewmodels.FitnessWorkoutEndViewModel
import com.unifit.unifit.presentation.viewmodels.FitnessWorkoutEndViewModelFactory

class ProfileFragment : Fragment() {
    private var binding : FragmentProfileBinding? = null
    private val sharedPreferences by lazy { requireContext().getSharedPreferences("user_data",
        Context.MODE_PRIVATE
    ) }
    private val fitnessWorkoutEndViewModel : FitnessWorkoutEndViewModel by activityViewModels{
        FitnessWorkoutEndViewModelFactory(sharedPreferences)
    }
    private val progressBar by lazy { binding?.progressBarBMI }
    private val user by lazy { fitnessWorkoutEndViewModel.user }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnEdit?.let {
            EdgeToEdgeHelper.updateMarginToStatusBarInsets(it)
        }
        binding?.btnSignOut?.let {
            EdgeToEdgeHelper.updateMarginToSystemBarsInsets(it)
        }

        binding?.btnEdit?.setOnClickListener {
            onEditClicked()
        }

        binding?.tvAge?.text = getString(R.string.age_text, user.age)
        binding?.tvGender?.text = getString(R.string.gender_text, user.gender)
        binding?.tvBodyLevel?.text = getString(R.string.body_level_text, user.bodyLevel)
        binding?.tvDesiredWeight?.text = getString(R.string.desired_weight_text, user.desiredWeight)

        fitnessWorkoutEndViewModel.bmi.observe(viewLifecycleOwner) {
            it?.let{
                binding?.tvBMIValue?.text = fitnessWorkoutEndViewModel.getBMIinString()
                progressBar?.progress = it.toInt()
                fitnessWorkoutEndViewModel.getColourOfProgressBar()?.let { color ->
                    progressBar?.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this.requireContext(), color))
                }
                return@observe
            }
            binding?.tvBMIValue?.text = getString(R.string.enter_weight_height)
            progressBar?.visibility = View.INVISIBLE
        }
    }

    private fun onEditClicked() {
        val action = ProfileFragmentDirections.actionProfileFragmentToTestFragment()
        findNavController().navigate(action)
    }

}