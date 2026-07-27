package com.traffic.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith

private const val SLIDE_DURATION_MS = 300

/** 탭/화면 진입: 새 화면이 오른쪽에서 들어오고 이전 화면은 왼쪽으로 나감 */
fun headUpSlideTransition(): ContentTransform =
    slideInHorizontally(
        animationSpec = tween(SLIDE_DURATION_MS),
        initialOffsetX = { fullWidth -> fullWidth },
    ) togetherWith slideOutHorizontally(
        animationSpec = tween(SLIDE_DURATION_MS),
        targetOffsetX = { fullWidth -> -fullWidth },
    )

/** 뒤로가기: 이전 화면이 왼쪽에서 들어오고 현재 화면은 오른쪽으로 나감 */
fun headUpSlidePopTransition(): ContentTransform =
    slideInHorizontally(
        animationSpec = tween(SLIDE_DURATION_MS),
        initialOffsetX = { fullWidth -> -fullWidth },
    ) togetherWith slideOutHorizontally(
        animationSpec = tween(SLIDE_DURATION_MS),
        targetOffsetX = { fullWidth -> fullWidth },
    )
