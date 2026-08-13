package com.system.traffic.presentation.screens.bus_arrive.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.system.traffic.design.R
import com.system.traffic.design.ui.theme.TrafficTheme

@Composable
fun NextBusStopSection(
    nextBusStopName: String,
) {
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = "${nextBusStopName}${stringResource(R.string.bus_arrive_direction)}",
        style = TrafficTheme.typography.busArriveBody,
        textAlign = TextAlign.Start,
        color = TrafficTheme.colors.textPrimary
    )
}