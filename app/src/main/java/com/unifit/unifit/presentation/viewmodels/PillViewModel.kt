package com.unifit.unifit.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.domain.usecases.GetPillsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PillViewModel  @Inject constructor(
    private val getPillsUseCase: GetPillsUseCase
) : ViewModel(){

    private val _pillsLiveData = MutableLiveData<List<PillEntity>>()
    val pillsLiveData: LiveData<List<PillEntity>> get() = _pillsLiveData

    var chosenPill: PillEntity? = null

    init {
        getPills()
    }
    fun getPills() {
        viewModelScope.launch {
            getPillsUseCase.execute().collect { pills ->
                _pillsLiveData.value = pills
            }
        }
    }
}