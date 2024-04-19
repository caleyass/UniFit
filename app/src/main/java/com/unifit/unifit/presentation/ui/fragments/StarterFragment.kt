package com.unifit.unifit.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.unifit.unifit.databinding.FragmentStarterBinding

class StarterFragment : Fragment() {
    private var binding : FragmentStarterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStarterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.button?.setOnClickListener {
            onFitnessProgramClicked()
        }
    }
    private fun onFitnessProgramClicked() {
        val action = StarterFragmentDirections.actionStarterFragmentToFitnessFragment()
        findNavController().navigate(action)
    }
}