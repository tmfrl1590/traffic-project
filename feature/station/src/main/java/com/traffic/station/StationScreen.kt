package com.traffic.station

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.common.AdBannerView
import com.traffic.common.R
import com.traffic.common.Resource
import com.traffic.common.SearchArea
import com.traffic.domain.model.KeywordModel
import com.traffic.domain.model.StationModel
import com.traffic.station.component.KeywordArea
import com.traffic.station.component.SearchedStationListArea
import com.traffic.station.viewmodel.StationViewModel


@Composable
fun StationScreen(
    snackBarHostState: SnackbarHostState,
    stationViewModel: StationViewModel = hiltViewModel(),
    onStationCardClick: (String, String) -> Unit,
    onSearchStation: (String) -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    LaunchedEffect(Unit) {
        //logEvent(context, "StationScreen")
        stationViewModel.getLikeStationList()
    }

    val searchedStationList by stationViewModel.searchedStationList.collectAsStateWithLifecycle(initialValue = Resource.Idle())

    val keywordList by stationViewModel.keywordList.collectAsStateWithLifecycle(initialValue = listOf())

    StationContent(
        snackBarHostState = snackBarHostState,
        searchedStationList = searchedStationList,
        onStationCardClick = onStationCardClick,
        onSearchStation = onSearchStation,
        onFavoriteIconClick = onFavoriteIconClick,
        keywordList = keywordList,
    )
}

@Composable
private fun StationContent(
    snackBarHostState: SnackbarHostState,
    searchedStationList: Resource<List<StationModel>>,
    onStationCardClick: (String, String) -> Unit,
    onSearchStation: (String) -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
    keywordList: List<KeywordModel>,
) {
    var keyword by remember { mutableStateOf("") }

    LaunchedEffect(keywordList) {
        if (keyword.isEmpty() && keywordList.isNotEmpty()) {
            keyword = keywordList.first().keyword
            onSearchStation(keyword)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            SearchArea(
                keyword = keyword,
                onValueChange = { keyword = it },
                searchAction = { onSearchStation(keyword) },
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            KeywordArea(
                keywordList = keywordList,
                onKeywordClick = { clickedKeyword ->
                    onSearchStation(clickedKeyword)
                }
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFE5E7EB)
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            SearchedStationListArea(
                snackBarHostState = snackBarHostState,
                searchedStationList = searchedStationList,
                onStationCardClick = onStationCardClick,
                onFavoriteIconClick = onFavoriteIconClick
            )
        }

        /*AdBannerView(
            modifier = Modifier
                .padding(vertical = 16.dp)
        )*/

    }
}