package com.traffic.presentation.screens.bus_arrive.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RefreshButton(
    modifier: Modifier,
    remainingSeconds: Int,
    onClickRefresh: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClickRefresh,
        containerColor = Color(0xFF2563EB), // 프리미엄 딥블루
        contentColor = Color.White,
        shape = CircleShape,
        modifier = modifier.size(52.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val progress = remainingSeconds / 30f
            CircularProgressIndicator(
                progress = { progress },
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.2f),
                strokeWidth = 3.dp,
                modifier = Modifier.size(44.dp)
            )
            Text(
                text = "$remainingSeconds",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}