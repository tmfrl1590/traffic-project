package com.system.traffic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.enum.AppFontSize
import com.system.traffic.core.enum.AppThemeType
import com.traffic.domain.usecase.datastore.GetAppFontSizeUseCase
import com.traffic.domain.usecase.datastore.GetAppThemeTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAppFontSizeUseCase: GetAppFontSizeUseCase,
    getAppThemeTypeUseCase: GetAppThemeTypeUseCase,
): ViewModel(){

    val savedFontScale = getAppFontSizeUseCase()
        .map { AppFontSize.getScaleFromText(fontSizeText = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = AppFontSize.MEDIUM.scale,
        )

    val savedThemeType = getAppThemeTypeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = AppThemeType.LIGHT.themeName,
        )
}