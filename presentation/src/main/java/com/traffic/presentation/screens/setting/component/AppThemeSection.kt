package com.traffic.presentation.screens.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.traffic.core.enum.AppThemeType
import com.traffic.design.ui.theme.MainColor

@Composable
fun AppThemeSection(
    selectedTheme: String,
    onClickTheme: (String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "앱 테마 설정",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF0F172A)
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color(0xFFF1F5F9), // 얇고 깨끗한 구분선
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AppThemeType.entries.forEach { appThemeType ->
                    AppThemeCard(
                        modifier = Modifier
                            .weight(1f)
                        ,
                        themeText = appThemeType.themeName,
                        fontColor = if (appThemeType.themeName == selectedTheme) Color.White else Color.Black,
                        containerColor = if (appThemeType.themeName == selectedTheme) MainColor else Color.LightGray,
                        onClickTheme = { onClickTheme(appThemeType.themeName) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AppThemeCard(
    modifier: Modifier = Modifier,
    themeText: String,
    fontColor: Color,
    containerColor: Color,
    onClickTheme: () -> Unit,
) {
    Card(
        onClick = onClickTheme,
        modifier = modifier
            .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
            ,
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = themeText,
                color = fontColor,
            )
        }
    }
}