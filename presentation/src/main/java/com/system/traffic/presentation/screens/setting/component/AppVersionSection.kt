package com.system.traffic.presentation.screens.setting.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.system.traffic.presentation.screens.setting.SettingTestTags
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.traffic.design.R
import com.system.traffic.design.ui.theme.TrafficTheme

@Composable
fun AppVersionSection(
    appVersion: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .testTag(tag = SettingTestTags.APP_VERSION_SECTION)
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.setting_app_info_title),
                    style = TrafficTheme.typography.sectionTitle,
                    color = TrafficTheme.colors.textPrimary
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = TrafficTheme.colors.divider,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.setting_app_version),
                    style = TrafficTheme.typography.sectionBody1,
                    color = TrafficTheme.colors.textPrimary
                )
                Text(
                    text = "v $appVersion",
                    style = TrafficTheme.typography.sectionBody1,
                    color = TrafficTheme.colors.textPrimary
                )
            }
        }
    }
}