package com.system.traffic.design.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = this.clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() },
    onClick = onClick
)

// 햅틱을 치고 액션을 실행하는 1줄 확장 함수
inline fun HapticFeedback.performAnd(
    type: HapticFeedbackType = HapticFeedbackType.LongPress,
    crossinline action: () -> Unit
): () -> Unit = {
    performHapticFeedback(type)
    action()
}