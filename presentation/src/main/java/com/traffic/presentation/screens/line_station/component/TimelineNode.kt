package com.traffic.presentation.screens.line_station.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TimelineNode(
    isFirst: Boolean,
    isLast: Boolean,
    color: Color,
    circleRadius: Dp = 6.dp,
    strokeWidth: Dp = 3.dp,
) {
    Canvas(
        modifier = Modifier
            .fillMaxHeight()
            .width(40.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val startY = if (isFirst) centerY else 0f
        val endY = if (isLast) centerY else size.height

        drawLine(
            color = color,
            start = Offset(centerX, startY),
            end = Offset(centerX, endY),
            strokeWidth = strokeWidth.toPx()
        )

        drawCircle(
            color = color,
            radius = circleRadius.toPx(),
            center = Offset(centerX, centerY),
            style = Stroke(width = strokeWidth.toPx())
        )

        drawCircle(
            color = Color.White,
            radius = (circleRadius - (strokeWidth / 2)).toPx(),
            center = Offset(centerX, centerY)
        )
    }
}