package com.traffic.presentation.screens.station.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.common.noRippleClickable
import com.traffic.domain.model.KeywordModel

@Composable
fun KeywordListSection(
    keywordList: List<KeywordModel>,
    onClickKeyword: (String) -> Unit,
    onClickDeleteKeyword: (String) -> Unit,
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
                    items = keywordList.sortedByDescending { it.id },
                    key = { it.id }
                ) { keyword ->
                    KeywordCard(
                        keywordModel = keyword,
                        onKeywordClick = onClickKeyword,
                        onDeleteClick = { onClickDeleteKeyword(keyword.keyword) }
                    )
                }
            }
        }
    }
}

@Composable
private fun KeywordCard(
    keywordModel: KeywordModel,
    onKeywordClick: (String) -> Unit,
    onDeleteClick: () -> Unit
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
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = keywordModel.keyword,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Delete Keyword",
                tint = Color.Gray,
                modifier = Modifier
                    .size(16.dp)
                    .noRippleClickable { onDeleteClick() }
            )
        }
    }
}
