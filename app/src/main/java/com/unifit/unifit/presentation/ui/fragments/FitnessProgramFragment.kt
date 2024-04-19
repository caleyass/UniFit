package com.unifit.unifit.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unifit.unifit.databinding.FragmentFitnessProgramBinding
import com.unifit.unifit.presentation.adapter.FitnessCategoryAdapter
import com.unifit.unifit.presentation.adapter.FitnessWorkoutAdapter
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper
import com.unifit.unifit.presentation.viewmodel.FitnessViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FitnessProgramFragment : Fragment() {

    private var binding : FragmentFitnessProgramBinding? = null
    private val viewModel : FitnessViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFitnessProgramBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EdgeToEdgeHelper.updatePaddingToStatusBarInsets(binding?.backButton as View)
        initializeRecylerView()
    }

    private fun initializeRecylerView() {
        val adapter = FitnessWorkoutAdapter(::onFitnessProgramClicked)
        binding?.recyclerView?.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFitnessProgramWorkouts(getCategoryName()).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun getCategoryName():String{
        val args: FitnessProgramFragmentArgs by navArgs()
        return args.name
    }

    private fun onFitnessProgramClicked(categoryName:String) {
        Log.d("FragmentNavigation", "onFitnessProgramClicked: $categoryName")
        val action = FitnessFragmentDirections.actionFitnessFragmentToFitnessProgramFragment(categoryName)
        findNavController().navigate(action)
    }

}