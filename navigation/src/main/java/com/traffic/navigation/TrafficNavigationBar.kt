package com.traffic.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import com.traffic.design.component.noRippleClickable
import com.traffic.design.ui.theme.TrafficTheme

@Composable
fun TrafficNavigationBar(
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .requiredHeight(64.dp)
            .drawBehind {
                drawLine(
                    color = Color(0xFFEBEAE5),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx(),
                )
            },
        containerColor = TrafficTheme.colors.mainBackground,
    ){
        TOP_LEVEL_DESTINATIONS.forEach { (topLevelDestination, data) ->
            TrafficNavigationBarItem(
                modifier = Modifier
                    .weight(1f)
                ,
                onSelectKey = { onSelectKey(topLevelDestination) },
                imageVector = data.icon,
                iconColor = if(topLevelDestination == selectedKey) TrafficTheme.colors.selectedBottomColor else TrafficTheme.colors.unSelectedBottomColor,
                contentDescription = "",
                label = data.title,
                labelColor = if(topLevelDestination == selectedKey) TrafficTheme.colors.selectedBottomColor else TrafficTheme.colors.unSelectedBottomColor,
            )
        }
    }
}

@Composable
private fun TrafficNavigationBarItem(
    modifier: Modifier = Modifier,
    onSelectKey: () -> Unit,
    imageVector: ImageVector,
    iconColor: Color,
    contentDescription: String,
    label: String,
    labelColor: Color,
) {
    Column(
        modifier = modifier
            .noRippleClickable {onSelectKey()}
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp)
            ,
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = iconColor,
        )
        Text(
            text = label,
            color = labelColor,
            style = TextStyle(
                fontSize = 14.sp
            )
        )
    }
}