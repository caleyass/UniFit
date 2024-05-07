package com.unifit.unifit.presentation.viewmodels

import android.content.SharedPreferences
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unifit.unifit.R

class FitnessWorkoutEndViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    val bmiRanges = arrayOf(18.5f, 24.9f, 29.9f, 34.9f, 39.9f)
    val bmiColors = arrayOf(
        R.color.underweight,
        R.color.normal_weight,
        R.color.overweight,
        R.color.obese_class_i,
        R.color.obese_class_ii
    )

    private val _weight = MutableLiveData<Int?>()
    val weight: LiveData<Int?> = _weight

    private val _height = MutableLiveData<Int?>()
    val height: LiveData<Int?> = _height

    private val _bmi = MutableLiveData<Float?>()
    val bmi: LiveData<Float?> = _bmi
    init {
        _weight.value = loadWeightFromSharedPreferences()
        _height.value = loadHeightFromSharedPreferences()
        calculateBMI()
    }
    private fun loadWeightFromSharedPreferences(): Int? {
        return sharedPreferences.getInt("weight", 0).takeIf { it > 0 }
    }

    private fun loadHeightFromSharedPreferences(): Int? {
        return sharedPreferences.getInt("height", 0).takeIf { it > 0 }
    }
    fun calculateBMIValue(weight: Int, height: Int): Float {
        return (weight.toFloat() * 100 * 100)/ (height.toFloat() * height.toFloat())
    }

    fun calculateBMI() {
        val currentWeight = weight.value ?: return
        val currentHeight = height.value ?: return

        val bmiValue = calculateBMIValue(currentWeight, currentHeight)
        _bmi.value = bmiValue
    }

    fun saveWeightHeight(weight: Int, height: Int) {
        _weight.value = weight
        _height.value = height
        calculateBMI()
    }

    fun getColourOfProgressBar() : Int? {
        for (i in bmiRanges.indices) {
            bmi.value?.let {
                if (it < bmiRanges[i]) {
                    return bmiColors[i]
                }
            }
        }
        return null
    }

    fun getBMIinString() : String{
        return String.format("%.1f", bmi.value)
    }

    override fun onCleared() {
        super.onCleared()
        with(sharedPreferences.edit()) {
            weight.value?.let { putInt("weight", it) }
            height.value?.let { putInt("height", it) }
            apply()
        }
    }
}

class FitnessWorkoutEndViewModelFactory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FitnessWorkoutEndViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FitnessWorkoutEndViewModel(sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
