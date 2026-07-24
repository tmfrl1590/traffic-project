package com.traffic.presentation.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent
}

@Singleton
class UiEventBus @Inject constructor() {
    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()
    suspend fun sendEvent(uiEvent: UiEvent) {
        _event.emit(uiEvent)
    }
}