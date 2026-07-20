package com.traffic.presentation.screens.line_station.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.system.traffic.core.enum.BusDirection
import com.traffic.design.noRippleClickable

@Composable
fun BusDirectionTabSection(
    selectedBusDirectionIndex: Int,
    onClickBusDirectionTab: (Int) -> Unit,
) {
    val tabs = listOf(BusDirection.UP.busDirectionName, BusDirection.DOWN.busDirectionName)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color(0xFFE5E7EB), shape = RoundedCornerShape(20.dp))
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedBusDirectionIndex == index
            val tabBgColor = if (isSelected) Color(0xFF1E88E5) else Color.Transparent
            val tabTextColor = if (isSelected) Color.White else Color.Black

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(tabBgColor, shape = RoundedCornerShape(18.dp))
                    .noRippleClickable { onClickBusDirectionTab(index) }
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = tabTextColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}