package com.traffic.presentation.screens.line_station.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.traffic.design.ui.theme.TrafficTheme
import com.traffic.presentation.model.LineStationItemModel

@Composable
fun LineStationListSection(
    currentList: List<LineStationItemModel>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = currentList,
            key = { index, item ->
                "${item.busStopId}_$index"
            }
        ) { index, station ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimelineNode(
                    isFirst = (index == 0),
                    isLast = (index == currentList.lastIndex),
                    color = Color(0xFF90CAF9)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = station.busStopName,
                        style = TrafficTheme.typography.sectionTitle,
                        color = TrafficTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = station.arsId?.toString() ?: "번호 없음",
                        style = TrafficTheme.typography.sectionBody1,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}