package com.system.traffic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.enum.AppFontSize
import com.traffic.domain.usecase.datastore.GetAppFontSizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAppFontSizeUseCase: GetAppFontSizeUseCase,
): ViewModel(){

    val savedFontScale = getAppFontSizeUseCase()
        .map { AppFontSize.getScaleFromText(fontSizeText = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = AppFontSize.MEDIUM.scale,
        )
}