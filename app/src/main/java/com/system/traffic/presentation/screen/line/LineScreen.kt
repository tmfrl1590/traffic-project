package com.system.traffic.presentation.screen.line

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.system.traffic.R
import com.system.traffic.presentation.component.BannersAds
import com.system.traffic.presentation.component.LineInfo
import com.system.traffic.presentation.component.NoDataComponent
import com.system.traffic.presentation.component.SearchBox

@Composable
fun LineScreen(
    lineViewModel: LineViewModel = hiltViewModel(),
) {
    var keyword by remember {
        mutableStateOf("")
    }

    val searchedLineList by lineViewModel.searchedLineList.collectAsState(initial = listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBox(
            text = stringResource(R.string.line),
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { lineViewModel.getSearchedLineList(keyword = keyword) },
        )

        if (searchedLineList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(0.9f)
            ) {
                itemsIndexed(
                    items = searchedLineList
                ) { index, lineModel ->
                    LineInfo(
                        lineModel = lineModel,
                    )
                }
            }
        } else {
            NoDataComponent(
                modifier = Modifier
                    .weight(0.9f),
                text = stringResource(R.string.searched_line_no_data)
            )
        }

        BannersAds(
            modifier = Modifier
                .weight(0.1f)
        )
    }
}

