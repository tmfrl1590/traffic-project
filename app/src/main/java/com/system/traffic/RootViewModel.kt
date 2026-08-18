package com.system.traffic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.enums.AppThemeType
import com.system.traffic.domain.usecase.datastore.GetAppFontSizeUseCase
import com.system.traffic.domain.usecase.datastore.GetAppThemeTypeUseCase
import com.system.traffic.domain.usecase.network.GetNetworkStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    getAppFontSizeUseCase: GetAppFontSizeUseCase,
    getAppThemeTypeUseCase: GetAppThemeTypeUseCase,
    getNetworkStatusUseCase: GetNetworkStatusUseCase,
): ViewModel(){

    val isNetworkConnected = getNetworkStatusUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = true,
        )

    val appConfig: StateFlow<AppConfig?> = combine(
        getAppThemeTypeUseCase(),
        getAppFontSizeUseCase(),
    ) { themeType, fontSize ->
        AppConfig(
            themeType = themeType,
            fontScale = fontSize.scale,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = null, // 로드 전 = null
    )
}

data class AppConfig(
    val themeType: AppThemeType,
    val fontScale: Float,
)