package com.hakmar.employeelivetracking.features.qr_analyze.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.features.qr_analyze.domain.repository.QrAnalysisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRAnalysisViewModel @Inject constructor(
    val repository: QrAnalysisRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QRAnalysisState())
    val state = _state.asStateFlow()


    fun startScanning() {
        viewModelScope.launch {
            repository.startScanning().collect { data ->
                if (data.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            result = data
                        )
                    }
                }
            }
        }
    }
}