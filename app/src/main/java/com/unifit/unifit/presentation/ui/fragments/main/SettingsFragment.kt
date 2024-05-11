package com.unifit.unifit.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.unifit.unifit.databinding.FragmentProfileBinding
import com.unifit.unifit.databinding.FragmentSettingsBinding
import com.unifit.unifit.databinding.FragmentStarterBinding

class SettingsFragment : Fragment() {
    private var binding : FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.cvProfile?.setOnClickListener {
            onProfileClicked()
        }
    }

    private fun onProfileClicked() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToProfileFragment()
        findNavController().navigate(action)
    }
}