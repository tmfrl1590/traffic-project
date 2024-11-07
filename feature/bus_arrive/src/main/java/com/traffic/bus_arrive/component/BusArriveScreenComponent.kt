package com.traffic.bus_arrive.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.traffic.bus_arrive.util.busArriveScreenTitleText
import com.traffic.domain.model.StationModel

@Composable
fun StationFavoriteIcon(
    stationModel: StationModel,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    IconButton(
        onClick = { onFavoriteIconClick(stationModel) }
    ) {
        Icon(
            imageVector = if (stationModel.selected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = Color.Red,
        )
    }
}

@Composable
fun BusArriveScreenTitle(
    stationInfo: StationModel,
) {
    Text(
        text = busArriveScreenTitleText(stationInfo),
        fontSize = 20.sp,
        color = Color.Black
    )
}