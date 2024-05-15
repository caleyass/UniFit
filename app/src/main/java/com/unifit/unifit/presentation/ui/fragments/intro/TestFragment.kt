package com.unifit.unifit.presentation.ui.fragments.intro

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.unifit.unifit.R
import com.unifit.unifit.databinding.FragmentTestBinding
import com.unifit.unifit.domain.data.User
import com.unifit.unifit.presentation.ui.utils.EdgeToEdgeHelper.updateMarginToSystemBarsInsets

class TestFragment : Fragment() {
    private var binding : FragmentTestBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.linearLayout?.let { updateMarginToSystemBarsInsets(it) }

        binding?.btnSave?.setOnClickListener {

        }
        binding?.agePicker?.minValue = 10
        binding?.agePicker?.maxValue = 100
        binding?.agePicker?.value = 30

        binding?.weightPicker?.minValue = 20
        binding?.weightPicker?.maxValue = 150
        binding?.weightPicker?.value = 55

        binding?.heightPicker?.minValue = 100
        binding?.heightPicker?.maxValue = 250
        binding?.heightPicker?.value = 170

        binding?.desiredWeightPicker?.minValue = 20
        binding?.desiredWeightPicker?.maxValue = 100
        binding?.desiredWeightPicker?.value = 55

        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(this.requireContext(), R.array.gender_options, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val adapter2: ArrayAdapter<*> = ArrayAdapter.createFromResource(this.requireContext(), R.array.body_level_options, R.layout.spinner_item)
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item)
        //TODO BETTER DESIGN AND ARCHITECTURE
        binding?.spinnerGender?.adapter = adapter
        binding?.spinnerLevel?.adapter = adapter2

        binding?.btnSave?.setOnClickListener {
            val name = binding?.etName?.text?.toString()
            val age = binding?.agePicker?.value
            val weight = binding?.weightPicker?.value
            val height = binding?.heightPicker?.value
            val desiredWeight = binding?.desiredWeightPicker?.value
            val gender =  binding?.spinnerGender?.selectedItem.toString()
            val bodyLevel = binding?.spinnerLevel?.selectedItem.toString()
            val user =
                User(userId = "1",
                    email = "",
                    name = name ?: "",
                    age = age ?: 0,
                    gender = gender ?: "",
                    weight = weight ?: 0,
                    height = height ?: 0,
                    bodyLevel = bodyLevel ?: "",
                    desiredWeight = desiredWeight ?: 0
                )
            saveUser(user)
            navigateToStarterFragment()
        }
    }

    private fun saveUser(user: User) {
        val prefs = requireActivity().getSharedPreferences("user_data", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("user", Gson().toJson(user)).apply()
        Log.d("TAG", "saveUser: $user")
    }

    private fun navigateToStarterFragment() {
        findNavController().navigate(TestFragmentDirections.actionTestFragmentToStarterFragment())
    }
}