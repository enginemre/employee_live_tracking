package com.hakmar.employeelivetracking.common.presentation.ui

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventManager @Inject constructor() {

    private val _eventFlow = MutableSharedFlow<SharedEvent>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        replay = 1,
    )
    val eventFlow = _eventFlow.asSharedFlow()
    fun postEvent(sharedEvent: SharedEvent) {
        _eventFlow.tryEmit(sharedEvent)
    }

}