package com.system.traffic.presentation.screens.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.usecase.datastore.GetAppFontSizeUseCase
import com.system.traffic.domain.usecase.datastore.GetAppThemeTypeUseCase
import com.system.traffic.domain.usecase.datastore.SetAppThemeTypeUseCase
import com.system.traffic.domain.usecase.datastore.SetFontSizeUseCase
import com.system.traffic.domain.usecase.pinned_bus.ResetPinnedBusUseCase
import com.system.traffic.presentation.event.UiEvent
import com.system.traffic.presentation.event.UiEventBus
import com.system.traffic.presentation.screens.setting.action.SettingAction
import com.system.traffic.presentation.screens.setting.effect.SettingEffect
import com.system.traffic.presentation.screens.setting.state.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _effect = MutableSharedFlow<SettingEffect>()
    val effect = _effect.asSharedFlow()

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

    fun onAction(action: SettingAction) {
        when (action) {
            SettingAction.OnClickInquire -> emitEffect(SettingEffect.SendInquireEmail)
            SettingAction.OnClickOpenSource -> emitEffect(SettingEffect.OpenOssLicenses)
            is SettingAction.OnClickFontSize -> selectFontSize(fontSizeText = action.fontSizeText)
            is SettingAction.OnClickTheme -> selectTheme(themeType = action.themeType)
            SettingAction.OnClickReset -> showResetConfirmDialog()
            SettingAction.OnDismissResetDialog -> dismissResetConfirmDialog()
            SettingAction.OnClickResetConfirm -> resetPinnedBusData()
        }
    }

    private fun emitEffect(effect: SettingEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun selectFontSize(fontSizeText: String){
        viewModelScope.launch {
            setFontSizeUseCase(fontSizeText = fontSizeText)
        }
    }

    private fun selectTheme(themeType: String){
        viewModelScope.launch {
            setAppThemeTypeUseCase(themeType = themeType)
        }
    }

    private fun showResetConfirmDialog(){
        _state.update { it.copy(isShowResetConfirmDialog = true) }
    }

    private fun dismissResetConfirmDialog(){
        _state.update { it.copy(isShowResetConfirmDialog = false) }
    }

    private fun resetPinnedBusData() {
        viewModelScope.launch {
            _state.update { it.copy(isShowResetConfirmDialog = false) }
            resetPinnedBusUseCase()
            uiEventBus.sendEvent(UiEvent.ShowSnackBar(message = "데이터가 초기화되었습니다."))
        }
    }
}