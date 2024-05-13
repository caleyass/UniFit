package com.unifit.unifit.presentation.ui.fragments.pill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.unifit.unifit.databinding.FragmentPillInfoBinding
import com.unifit.unifit.presentation.viewmodels.PillViewModel

class PillInfoFragment : Fragment() {
    private var binding : FragmentPillInfoBinding? = null
    private val pillViewModel : PillViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPillInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        pillViewModel.chosenPill = null
    }
}