package com.traffic.station.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.domain.model.KeywordModel

@Composable
fun KeywordArea(
    keywordList: List<KeywordModel>,
    onKeywordClick: (String) -> Unit = {}
) {
    if (keywordList.isNotEmpty()) {
        Column {
            Text(
                text = "최근 검색어",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(bottom = 4.dp)
            )

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = keywordList,
                    key = { it.id }
                ) { keyword ->
                    KeywordChip(
                        keywordModel = keyword,
                        onKeywordClick = onKeywordClick
                    )
                }
            }
        }
    }
}

@Composable
private fun KeywordChip(
    keywordModel: KeywordModel,
    onKeywordClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onKeywordClick(keywordModel.keyword) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = keywordModel.keyword,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        }
    }
}
