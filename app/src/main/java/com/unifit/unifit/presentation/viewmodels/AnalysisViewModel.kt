package com.unifit.unifit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifit.unifit.data.local.entity.AnalysisEntity
import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.domain.usecases.GetAnalysisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor (
    private val getAnalysisUseCase: GetAnalysisUseCase
) : ViewModel() {
    private val _analysisLiveData = MutableLiveData<List<AnalysisEntity>>()
    val analysisLiveData: LiveData<List<AnalysisEntity>> get() = _analysisLiveData

    var chosenAnalysis: AnalysisEntity? = null

    init {
        getPills()
    }
    fun getPills() {
        viewModelScope.launch {
            getAnalysisUseCase.execute().collect { pills ->
                _analysisLiveData.value = pills
            }
        }
    }
}