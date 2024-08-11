package com.traffic.presentation.screen.station

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavHostController
import com.traffic.presentation.R
import com.traffic.presentation.component.NoDataComponent
import com.traffic.presentation.component.SearchBox
import com.traffic.presentation.component.StationInfo


@Composable
fun StationScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        stationViewModel.getLikeStationList()
    }

    var keyword by remember {
        mutableStateOf("")
    }

    val searchedStationList by stationViewModel.searchResult.collectAsState(initial = listOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBox(
            modifier = Modifier
                .weight(0.15f),
            text = stringResource(R.string.like_station),
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { stationViewModel.getSearchedStationList(keyword = keyword) },
        )

        if (searchedStationList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(0.75f)
            ) {
                items(searchedStationList.size) { index ->
                    StationInfo(
                        searchedStationList[index],
                        stationViewModel,
                        navHostController,
                    )
                }
            }
        } else {
            NoDataComponent(
                modifier = Modifier
                    .weight(0.9f),
                text = stringResource(R.string.searched_station_no_data)
            )
        }

        /*BannersAds(
            modifier = Modifier
                .weight(0.1f)
        )*/
    }
}