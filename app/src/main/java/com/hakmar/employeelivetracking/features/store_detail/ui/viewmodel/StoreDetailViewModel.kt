package com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoreDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        val id: String? = savedStateHandle["storeId"]
        id?.let {
            Log.d("StoreDetail", id)
        }
    }
}