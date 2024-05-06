package com.unifit.unifit.presentation.ui.fragments.fitness

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.unifit.unifit.databinding.FragmentFitnessBinding
import com.unifit.unifit.presentation.adapter.FitnessCategoryAdapter
import com.unifit.unifit.presentation.viewmodels.FitnessCategoryViewModel
import com.unifit.unifit.presentation.viewmodels.FitnessProgramExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FitnessFragment : Fragment() {

    private var binding : FragmentFitnessBinding? = null
    private val viewModel : FitnessCategoryViewModel by viewModels()

    private val sharedViewModel : FitnessProgramExerciseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFitnessBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecylerView()
    }

    private fun initializeRecylerView() {
        val adapter = FitnessCategoryAdapter(::onFitnessProgramClicked)
        binding?.recyclerView?.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowFitnessCategories.collectLatest {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                binding?.progressBar?.isVisible = (it.append is LoadState.Loading) || (it.refresh is LoadState.Loading)
            }
        }
    }

    private fun onFitnessProgramClicked(categoryName:String) {
        Log.d("FragmentNavigation", "onFitnessProgramClicked: $categoryName")
        sharedViewModel.category = categoryName
        val action = FitnessFragmentDirections.actionFitnessFragmentToFitnessProgramFragment()
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}