package com.traffic.presentation.screens.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.domain.usecase.datastore.GetAppFontSizeUseCase
import com.traffic.domain.usecase.datastore.SetFontSizeUseCase
import com.traffic.presentation.screens.setting.state.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val setFontSizeUseCase: SetFontSizeUseCase,
    private val getAppFontSizeUseCase: GetAppFontSizeUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(value = SettingState())
    val state: StateFlow<SettingState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getAppFontSizeUseCase().collectLatest { fontSize ->
                _state.update { it.copy(selectedFontSize = fontSize) }
            }
        }
    }

    fun selectFontSize(fontSizeText: String){
        viewModelScope.launch {
            setFontSizeUseCase(fontSizeText = fontSizeText)
        }
    }
}