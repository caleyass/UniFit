package com.unifit.unifit.presentation.ui.fragments.pill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.unifit.unifit.databinding.FragmentPillBinding
import com.unifit.unifit.presentation.adapter.PillAdapter
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper
import com.unifit.unifit.presentation.viewmodels.PillViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PillFragment : Fragment() {
    private var binding : FragmentPillBinding? = null
    private val pillViewModel : PillViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPillBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivAddPill?.let { EdgeToEdgeHelper.updateMarginToStatusBarInsets(it) }

        binding?.rvPills?.adapter = PillAdapter()
        pillViewModel.pillsLiveData.observe(viewLifecycleOwner){
            (binding?.rvPills?.adapter as PillAdapter).submitList(it)
        }
        binding?.ivAddPill?.setOnClickListener {
            navigateToPillInfoFragment()
        }
    }

    private fun navigateToPillInfoFragment(){
        val action = PillFragmentDirections.actionPillFragmentToPillInfoFragment()
        findNavController().navigate(action)
    }


}