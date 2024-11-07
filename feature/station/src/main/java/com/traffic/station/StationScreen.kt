package com.traffic.station

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.common.R
import com.traffic.common.Resource
import com.traffic.common.SearchArea
import com.traffic.domain.model.StationModel
import com.traffic.station.component.SearchedStationListArea
import com.traffic.station.viewmodel.StationViewModel


@Composable
fun StationScreen(
    snackBarHostState: SnackbarHostState,
    stationViewModel: StationViewModel = hiltViewModel(),
    onStationCardClick: (String) -> Unit,
    onSearchStation: (String) -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    LaunchedEffect(Unit) {
        //logEvent(context, "StationScreen")
        stationViewModel.getLikeStationList()
    }

    val searchedStationList by stationViewModel.searchedStationList.collectAsStateWithLifecycle(initialValue = Resource.Idle())

    StationContent(
        snackBarHostState = snackBarHostState,
        searchedStationList = searchedStationList,
        onStationCardClick = onStationCardClick,
        onSearchStation = onSearchStation,
        onFavoriteIconClick = onFavoriteIconClick
    )
}

@Composable
private fun StationContent(
    snackBarHostState: SnackbarHostState,
    searchedStationList: Resource<List<StationModel>>,
    onStationCardClick: (String) -> Unit,
    onSearchStation: (String) -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    var keyword by remember { mutableStateOf("") }

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
            searchAction = { onSearchStation(keyword) },
        )

        SearchedStationListArea(
            modifier = Modifier
                .weight(0.85f),
            snackBarHostState = snackBarHostState,
            searchedStationList = searchedStationList,
            onStationCardClick = onStationCardClick,
            onFavoriteIconClick = onFavoriteIconClick
        )
    }
}

