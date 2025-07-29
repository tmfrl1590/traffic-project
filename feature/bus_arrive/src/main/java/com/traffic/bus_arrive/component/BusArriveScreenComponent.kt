package com.traffic.bus_arrive.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.bus_arrive.util.busArriveScreenTitleText
import com.traffic.common.R
import com.traffic.domain.model.StationModel

@Composable
fun StationFavoriteIcon(
    stationModel: StationModel,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    IconButton(
        onClick = { onFavoriteIconClick(stationModel) },
    ) {
        Icon(
            painter = painterResource(id = if (stationModel.selected) R.drawable.icon_selected_star else R.drawable.icon_unselected_star),
            contentDescription = "Favorite",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
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