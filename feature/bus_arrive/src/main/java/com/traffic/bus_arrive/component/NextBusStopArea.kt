package com.traffic.bus_arrive.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.common.R

@Composable
fun NextBusStopArea(
    nextBusStopName: String,
) {
    Text(
        text = "${nextBusStopName}${stringResource(R.string.bus_arrive_direction)}",
        fontSize = 16.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 20.dp)
    )
}