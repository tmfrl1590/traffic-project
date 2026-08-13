package com.system.traffic.presentation.screens.setting.component

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
import androidx.compose.ui.platform.testTag
import com.system.traffic.presentation.screens.setting.SettingTestTags
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.system.traffic.design.ui.theme.MainColor
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.design.ui.theme.White
import com.system.traffic.design.R

@Composable
fun ResetPinnedBusSection(
    onClickReset: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .testTag(tag = SettingTestTags.RESET_PINNED_BUS_SECTION)
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
                text = stringResource(R.string.setting_reset_pinned_bus_title),
                style = TrafficTheme.typography.sectionTitle,
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
            .testTag(tag = SettingTestTags.RESET_BUTTON)
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
                text = stringResource(R.string.setting_reset_pin_button),
                style = TrafficTheme.typography.button,
                color = White,
            )
        }
    }
}