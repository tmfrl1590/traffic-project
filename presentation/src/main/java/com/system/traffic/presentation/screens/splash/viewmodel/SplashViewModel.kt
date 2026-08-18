package com.system.traffic.presentation.screens.splash.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.design.R
import com.system.traffic.domain.usecase.datastore.GetIsFirstLoginUseCase
import com.system.traffic.domain.usecase.datastore.SetUpIsFirstLoginUseCase
import com.system.traffic.domain.usecase.file.InitState
import com.system.traffic.domain.usecase.file.InitStep
import com.system.traffic.domain.usecase.file.InitializeDataUseCase
import com.system.traffic.presentation.screens.splash.state.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val initializeDataUseCase: InitializeDataUseCase,
    private val setUpIsFirstLoginUseCase: SetUpIsFirstLoginUseCase,
    private val getIsFirstLoginUseCase: GetIsFirstLoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()

    init {
        // 생성 즉시 초기화 시작.
        // 화면은 콜백이 아닌 state(isComplete)를 관찰하므로,
        // ViewModel이 재생성되어도 새 인스턴스가 다시 시작해 신호가 유실되지 않는다.
        initializeData()
    }

    fun retry() {
        _state.update { it.copy(isError = false) }
        initializeData()
    }

    private fun initializeData() {
        // 스레드 전환은 데이터 레이어(suspend DAO + flowOn)가 책임지므로 디스패처 지정 불필요
        viewModelScope.launch {
            try {
                withTimeout(timeMillis = INIT_TIMEOUT_MS) {
                    if (getIsFirstLoginUseCase()) {
                        initializeDataUseCase()
                            .catch {
                                _state.update { s -> s.copy(isLoading = false, isError = true) }
                            }
                            .collect { initState ->
                                when (initState) {
                                    is InitState.Progress -> {
                                        _state.update {
                                            it.copy(
                                                isLoading = true,
                                                messageRes = initState.step.toMessageRes(),
                                                progress = initState.progress
                                            )
                                        }
                                    }
                                    is InitState.Complete -> {
                                        // 초기화가 "완료된 후"에만 최초 실행 플래그 저장
                                        // (도중에 앱이 죽으면 다음 실행에서 다시 초기화)
                                        setUpIsFirstLoginUseCase()
                                        _state.update {
                                            it.copy(
                                                isLoading = false,
                                                messageRes = R.string.splash_init_complete,
                                                progress = 1.0f,
                                                isComplete = true
                                            )
                                        }
                                    }
                                }
                            }
                    } else {
                        _state.update { it.copy(isComplete = true) }
                    }
                }
            } catch (e: TimeoutCancellationException) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }

    companion object {
        private const val INIT_TIMEOUT_MS = 30_000L
    }
}

@StringRes
private fun InitStep.toMessageRes(): Int = when (this) {
    InitStep.LOAD_STATIONS -> R.string.splash_init_load_stations
    InitStep.SAVE_STATIONS -> R.string.splash_init_save_stations
    InitStep.LOAD_LINES -> R.string.splash_init_load_lines
    InitStep.SAVE_LINES -> R.string.splash_init_save_lines
    InitStep.DONE -> R.string.splash_init_done
}
