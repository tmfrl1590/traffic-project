package com.system.traffic.presentation.screen.line

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.system.traffic.presentation.component.LineInfo
import com.system.traffic.presentation.component.SearchBox
import com.system.traffic.presentation.screen.CommonViewModel
import com.system.traffic.presentation.screen.station.StationViewModel

@Composable
fun LineScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
    lineViewModel: LineViewModel = hiltViewModel(),
) {
    var keyword by remember {
        mutableStateOf("")
    }

    val searchedLineList by lineViewModel.searchedLineList.collectAsState(initial = listOf())

    Column {
        SearchBox(
            text = "노선",
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { lineViewModel.getSearchedLineList(keyword = keyword) },
        )

        if (searchedLineList.isNotEmpty()) {
            LazyColumn() {
                itemsIndexed(
                    items = searchedLineList
                ) { index, lineModel ->
                    LineInfo(
                        lineModel = lineModel,
                        lineViewModel = lineViewModel,
                        stationViewModel = stationViewModel,
                        navHostController = navHostController
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    text = "검색 결과가 없습니다.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth()
                )
            }
        }
    }
}

