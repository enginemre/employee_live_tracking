package com.hakmar.employeelivetracking.common.presentation.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<EVENT> : ViewModel() {
    abstract fun onEvent(event: EVENT)
}