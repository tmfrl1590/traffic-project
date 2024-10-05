package com.traffic.presentation.screen.line

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.traffic.common.firebase.logEvent
import com.traffic.presentation.R
import com.traffic.presentation.screen.component.LineInfo
import com.traffic.presentation.screen.component.NoDataComponent
import com.traffic.presentation.screen.component.SearchArea

@Composable
fun LineScreen(
    context: Context,
    lineViewModel: LineViewModel = hiltViewModel(),
) {
    var keyword by remember {
        mutableStateOf("")
    }

    val searchedLineList by lineViewModel.searchedLineList.collectAsState(initial = listOf())

    LaunchedEffect(key1 = Unit) {
        logEvent(context, "LineScreen")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchArea(
            modifier = Modifier
                .weight(0.15f),
            text = stringResource(R.string.line),
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { lineViewModel.getSearchedLineList(keyword = keyword) },
        )

        if (searchedLineList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(0.75f)
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

        /*BannersAds(
            modifier = Modifier
                .weight(0.1f)
        )*/
    }
}

