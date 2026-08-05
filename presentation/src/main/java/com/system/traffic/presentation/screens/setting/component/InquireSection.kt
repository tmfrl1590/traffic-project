package com.system.traffic.presentation.screens.setting.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.system.traffic.design.R
import com.system.traffic.design.ui.theme.TrafficTheme

@Composable
fun InquireSection(
    onClickInquire: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
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
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.setting_inquire_title),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickInquire() }
                    .padding(vertical = 2.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.setting_inquire),
                        style = TrafficTheme.typography.sectionBody1,
                        color = TrafficTheme.colors.textPrimary,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.setting_inquire_description),
                        style = TrafficTheme.typography.sectionBody2,
                        color = Color(0xFF94A3B8)
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFFCBD5E1),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}