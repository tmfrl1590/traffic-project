package com.traffic.navigation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.traffic.design.R
import kotlinx.coroutines.launch

@Composable
fun DoubleBackToExitHandler(
    enabled: Boolean,
    snackbarHostState: SnackbarHostState,
    message: String = stringResource(R.string.double_back_to_exit),
    exitIntervalMs: Long = NavigationConstants.EXIT_BACK_PRESS_INTERVAL_MS,
    minIntervalMs: Long = NavigationConstants.EXIT_BACK_PRESS_MIN_INTERVAL_MS
) {
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()

    // enabled 상태가 변경되면(탭 전환 등) 자동으로 타이머를 리셋하기 위해 key값으로 지정
    var lastExitBackPressTimeMs by remember(enabled) { mutableLongStateOf(0L) }

    BackHandler(enabled = enabled) {
        val now = System.currentTimeMillis()
        val elapsed = now - lastExitBackPressTimeMs

        when {
            // 2회 연속 백클릭 완료 -> 앱 종료
            lastExitBackPressTimeMs != 0L && elapsed in minIntervalMs..exitIntervalMs -> {
                lastExitBackPressTimeMs = 0L
                activity?.finish()
            }
            // 1회 클릭 -> 스낵바 문구 노출 및 타임스탬프 기록
            lastExitBackPressTimeMs == 0L || elapsed > exitIntervalMs -> {
                lastExitBackPressTimeMs = now
                scope.launch {
                    snackbarHostState.showSnackbar(message = message)
                }
            }
            // 그 외 지나치게 빠른 연속 클릭 오작동은 무시
            else -> Unit
        }
    }
}
