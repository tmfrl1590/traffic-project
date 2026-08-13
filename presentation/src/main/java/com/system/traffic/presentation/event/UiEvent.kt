package com.system.traffic.presentation.event

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface UiEvent {
    data class ShowSnackBar(@param:StringRes val messageRes: Int) : UiEvent
}

@Singleton
class UiEventBus @Inject constructor() {
    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()
    suspend fun sendEvent(uiEvent: UiEvent) {
        _event.emit(uiEvent)
    }
}