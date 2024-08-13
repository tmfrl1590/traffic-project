package com.traffic.presentation.screen.station

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.traffic.presentation.screen.component.SearchArea


@Composable
fun StationScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        stationViewModel.getLikeStationList()
    }

    val searchedStationList by stationViewModel.searchResult.collectAsState(initial = listOf())

    var keyword by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchArea(
            modifier = Modifier
                .weight(0.15f),
            text = stringResource(R.string.station),
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { stationViewModel.getSearchedStationList(keyword = keyword) },
        )

        StationListArea(
            modifier = Modifier
                .weight(0.75f),
            stationViewModel = stationViewModel,
            navHostController = navHostController,
            searchedStationList = searchedStationList,
        )
    }
}