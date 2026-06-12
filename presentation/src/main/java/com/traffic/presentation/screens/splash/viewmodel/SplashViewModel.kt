package com.traffic.presentation.screens.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.domain.usecase.datastore.GetIsFirstLoginUseCase
import com.traffic.domain.usecase.datastore.SetUpIsFirstLoginUseCase
import com.traffic.domain.usecase.file.InitializeDataUseCase
import com.traffic.domain.usecase.file.InitState
import com.traffic.presentation.screens.splash.state.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val initializeDataUseCase: InitializeDataUseCase,
    private val setUpIsFirstLoginUseCase: SetUpIsFirstLoginUseCase,
    private val getIsFirstLoginUseCase: GetIsFirstLoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state


    fun initializeData(
        onComplete: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFirst = getIsFirstLoginUseCase()
            if (isFirst) {
                setUpIsFirstLoginUseCase()

                initializeDataUseCase().collect { state ->
                    withContext(Dispatchers.Main) {
                        when (state) {
                            is InitState.Progress -> {
                                _state.update {
                                    it.copy(
                                        isLoading = true,
                                        message = state.message,
                                        progress = state.progress
                                    )
                                }
                            }
                            is InitState.Complete -> {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        message = "완료",
                                        progress = 1.0f
                                    )
                                }
                                onComplete()
                            }
                        }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    onComplete()
                }
            }
        }
    }
}