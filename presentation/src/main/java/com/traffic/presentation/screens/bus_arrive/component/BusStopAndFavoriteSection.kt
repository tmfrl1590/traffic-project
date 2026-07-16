package com.traffic.presentation.screens.bus_arrive.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.design.R
import com.traffic.design.noRippleClickable

@Composable
fun BusStopAndFavoriteSection(
    busStopName: String,
    selected: Boolean,
    onClickFavorite: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = busStopName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Icon(
            painter = painterResource(id = if (selected) R.drawable.icon_selected_star else R.drawable.icon_unselected_star),
            contentDescription = "Favorite",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable {
                    onClickFavorite()
                }
        )
    }
}