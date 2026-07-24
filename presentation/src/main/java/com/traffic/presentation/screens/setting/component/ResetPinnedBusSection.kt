package com.traffic.presentation.screens.setting.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.design.ui.theme.MainColor
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.design.ui.theme.White

@Composable
fun ResetPinnedBusSection(
    onClickReset: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, color = TrafficTheme.colors.cardBorder),
        colors = CardDefaults.cardColors(
            containerColor = TrafficTheme.colors.mainBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "저장된 핀 리스트 초기화",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TrafficTheme.colors.textPrimary
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = TrafficTheme.colors.divider,
                thickness = 1.dp
            )

            ResetButton(
                onClickReset = onClickReset,
            )
        }
    }
}

@Composable
private fun ResetButton(
    onClickReset: () -> Unit,
) {
    Card(
        onClick = onClickReset,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = MainColor
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
            ,
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = "핀 초기화",
                color = White,
            )
        }
    }
}