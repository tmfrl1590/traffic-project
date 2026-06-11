package com.traffic.presentation.screens.station

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffic.common.SearchBarSection
import com.traffic.common.firebase.ScreenName
import com.traffic.common.firebase.TrackScreenView
import com.traffic.presentation.screens.station.action.StationAction
import com.traffic.presentation.screens.station.component.KeywordListSection
import com.traffic.presentation.screens.station.component.SearchedStationListSection
import com.traffic.presentation.screens.station.state.StationState
import com.traffic.presentation.screens.station.viewmodel.StationViewModel


@Composable
fun StationScreenRoute(
    stationViewModel: StationViewModel = hiltViewModel(),
    onStationCardClick: (String, String) -> Unit,
) {
    TrackScreenView(screenName = ScreenName.Station)

    val state by stationViewModel.state.collectAsStateWithLifecycle()

    StationScreen(
        state = state,
        onStationCardClick = onStationCardClick,
        onAction = stationViewModel::onAction
    )
}

@Composable
private fun StationScreen(
    state: StationState,
    onStationCardClick: (String, String) -> Unit,
    onAction: (StationAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            SearchBarSection(
                keyword = state.keyword,
                onValueChange = { onAction(StationAction.OnInputKeyword(keyword = it)) },
                searchAction = { onAction(StationAction.OnSearchStation) },
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            KeywordListSection(
                keywordList = state.keywordList,
                onClickKeyword = { keyword ->onAction(StationAction.OnClickKeyword(keyword = keyword)) },
                onClickDeleteKeyword = { keyword -> onAction(StationAction.OnDeleteKeyword(keyword = keyword))}
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFE5E7EB),
                modifier = Modifier.padding(vertical = 12.dp)
            )

            SearchedStationListSection(
                searchedStationList = state.searchedStationList,
                onClickStationCard = onStationCardClick,
                onClickFavoriteIcon = { onAction(StationAction.OnClickFavoriteIcon(stationModel = it))}
            )
        }
    }
}