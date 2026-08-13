package com.system.traffic.presentation.screens.setting.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.platform.testTag
import com.system.traffic.presentation.screens.setting.SettingTestTags
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.system.traffic.core.enums.AppThemeType
import com.system.traffic.design.ui.theme.MainColor
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.design.R

@Composable
fun AppThemeSection(
    selectedTheme: String,
    onClickTheme: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .testTag(tag = SettingTestTags.THEME_SECTION)
        ,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, color = TrafficTheme.colors.cardBorder),
        colors = CardDefaults.cardColors(
            containerColor = TrafficTheme.colors.mainBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.setting_theme_title),
                style = TrafficTheme.typography.sectionTitle,
                color = TrafficTheme.colors.textPrimary
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = TrafficTheme.colors.divider,
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
                        fontColor = if (appThemeType.themeName == selectedTheme) Color.White else TrafficTheme.colors.unselectedChipText,
                        containerColor = if (appThemeType.themeName == selectedTheme) MainColor else TrafficTheme.colors.unselectedChipBackground,
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
                style = TrafficTheme.typography.button,
                color = fontColor,
            )
        }
    }
}