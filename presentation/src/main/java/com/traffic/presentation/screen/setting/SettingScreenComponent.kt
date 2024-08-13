package com.traffic.presentation.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RowContent(
    title: String,
    content: String,
    onClick: () -> Unit = {},
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(60.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start
        )

        Text(
            text = content,
            textAlign = TextAlign.End
        )
    }
}