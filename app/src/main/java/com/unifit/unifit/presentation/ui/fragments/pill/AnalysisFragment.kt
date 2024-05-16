package com.unifit.unifit.presentation.ui.fragments.pill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unifit.unifit.databinding.FragmentAnalysisInfoBinding

class AnalysisFragment : Fragment() {
    private var binding : FragmentAnalysisInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentDate = System.currentTimeMillis()
        binding?.calendarView?.setDate(currentDate, false, true)
    }

}