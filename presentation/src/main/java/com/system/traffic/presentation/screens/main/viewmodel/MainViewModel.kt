package com.system.traffic.presentation.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import com.system.traffic.presentation.event.UiEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uiEventBus: UiEventBus
): ViewModel() {

    val uiEvent = uiEventBus.event
}