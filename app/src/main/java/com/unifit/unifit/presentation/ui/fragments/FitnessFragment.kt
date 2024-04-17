package com.unifit.unifit.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.unifit.unifit.data.remote.FirebaseApi
import com.unifit.unifit.databinding.FragmentFitnessBinding
import com.unifit.unifit.presentation.adapter.FitnessProgramAdapter
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper.updatePaddingToStatusBarInsets
import com.unifit.unifit.presentation.viewmodel.FitnessViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FitnessFragment : Fragment() {

    private var binding : FragmentFitnessBinding? = null
    private val viewModel : FitnessViewModel by viewModels()

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
        updatePaddingToStatusBarInsets(binding?.backButton as View)
    }

    private fun initializeRecylerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this.context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFitnessCategories().collect { data ->
                binding?.recyclerView?.adapter = FitnessProgramAdapter(data, ::onFitnessProgramClicked)

            }
        }
    }

    private fun onFitnessProgramClicked(categoryName:String) {
        Log.d("FragmentNavigation", "onFitnessProgramClicked: $categoryName")
        val action = FitnessFragmentDirections.actionFitnessFragmentToFitnessProgramFragment(categoryName)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}