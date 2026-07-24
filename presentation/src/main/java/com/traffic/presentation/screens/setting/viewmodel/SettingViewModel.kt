package com.traffic.presentation.screens.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.domain.usecase.datastore.GetAppFontSizeUseCase
import com.traffic.domain.usecase.datastore.GetAppThemeTypeUseCase
import com.traffic.domain.usecase.datastore.SetAppThemeTypeUseCase
import com.traffic.domain.usecase.datastore.SetFontSizeUseCase
import com.traffic.domain.usecase.pinned_bus.ResetPinnedBusUseCase
import com.traffic.presentation.event.UiEvent
import com.traffic.presentation.event.UiEventBus
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
    private val setAppThemeTypeUseCase: SetAppThemeTypeUseCase,
    private val getAppThemeTypeUseCase: GetAppThemeTypeUseCase,
    private val resetPinnedBusUseCase: ResetPinnedBusUseCase,
    private val uiEventBus: UiEventBus,
): ViewModel(){

    private val _state = MutableStateFlow(value = SettingState())
    val state: StateFlow<SettingState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getAppFontSizeUseCase().collectLatest { fontSize ->
                _state.update { it.copy(selectedFontSize = fontSize) }
            }
        }

        viewModelScope.launch {
            getAppThemeTypeUseCase().collectLatest { appThemeType ->
                _state.update { it.copy(selectedTheme = appThemeType) }
            }
        }
    }

    fun selectFontSize(fontSizeText: String){
        viewModelScope.launch {
            setFontSizeUseCase(fontSizeText = fontSizeText)
        }
    }

    fun selectTheme(themeType: String){
        viewModelScope.launch {
            setAppThemeTypeUseCase(themeType = themeType)
        }
    }

    fun showResetConfirmDialog(){
        _state.update { it.copy(isShowResetConfirmDialog = true) }
    }

    fun dismissResetConfirmDialog(){
        _state.update { it.copy(isShowResetConfirmDialog = false) }
    }

    fun resetPinnedBusData() {
        viewModelScope.launch {
            _state.update { it.copy(isShowResetConfirmDialog = false) }
            resetPinnedBusUseCase()
            uiEventBus.sendEvent(UiEvent.ShowSnackBar(message = "데이터가 초기화되었습니다."))
        }
    }
}