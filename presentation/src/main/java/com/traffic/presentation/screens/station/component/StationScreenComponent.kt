package com.traffic.presentation.screens.station.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffic.common.NoDataComponent
import com.traffic.common.R
import com.traffic.domain.model.StationModel

@Composable
fun SearchedStationListSection(
    searchedStationList: List<StationModel>,
    onClickStationCard: (String, String) -> Unit,
    onClickFavoriteIcon: (StationModel) -> Unit,
) {
    Box(
        modifier = Modifier
    ){
        if(searchedStationList.isEmpty()){
            NoDataComponent(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(R.string.searched_station_no_data)
            )
        } else {
            LazyColumn(
                modifier = Modifier
            ){
                items(
                    items = searchedStationList,
                    key = { item ->
                        item.arsId ?: ""
                    }
                ){ item ->
                    SearchedStationCard(
                        busStopName = item.busStopName ?: "",
                        stationModel = item,
                        onStationCardClick = onClickStationCard,
                        onFavoriteIconClick = {onClickFavoriteIcon(it)},
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchedStationCard(
    busStopName: String,
    stationModel: StationModel,
    onStationCardClick: (String, String) -> Unit,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    Card(
        onClick = { onStationCardClick(stationModel.arsId ?: "", stationModel.busStopId ?: "") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            SearchedStationInfoTopSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                busStopName = busStopName,
                stationModel = stationModel,
                onFavoriteIconClick = onFavoriteIconClick,
            )

            SearchedStationInfoBottomSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                nextBusStop = stationModel.nextBusStop ?: "",
                arsId = stationModel.arsId ?: ""
            )
        }
    }
}

@Composable
private fun SearchedStationInfoTopSection(
    modifier: Modifier,
    busStopName: String,
    stationModel: StationModel,
    onFavoriteIconClick: (StationModel) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BusStationName(
            busStopName = busStopName
        )

        StationInfoFavoriteIcon(
            modifier = Modifier,
            stationModel = stationModel,
            onFavoriteIconClick = { onFavoriteIconClick(stationModel) }
        )
    }
}

@Composable
private fun SearchedStationInfoBottomSection(
    modifier: Modifier,
    nextBusStop: String,
    arsId: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrentBusStopNameAndArsId(
            modifier = Modifier
                .fillMaxSize(),
            nextBusStop = nextBusStop,
            arsId = arsId,
        )
    }
}

@Composable
private fun BusStationName(
    busStopName: String,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = busStopName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
private fun StationInfoFavoriteIcon(
    modifier: Modifier,
    stationModel: StationModel,
    onFavoriteIconClick: (StationModel) -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = { onFavoriteIconClick(stationModel) }
    ){
        Icon(
            painter = painterResource(id = if (stationModel.selected) R.drawable.icon_selected_star else R.drawable.icon_unselected_star),
            contentDescription = "Favorite",
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun CurrentBusStopNameAndArsId(
    modifier: Modifier,
    nextBusStop: String,
    arsId: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ){
        Text(
            text = _root_ide_package_.com.traffic.presentation.screens.station.util.currentBusStopNameAndArsId(
                nextBusStop = nextBusStop,
                arsId = arsId
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchedStationCardPreview() {
    SearchedStationCard(
        busStopName = "서울역",
        stationModel = StationModel(
            stationNum = "11",
            busStopName = "서울역",
            nextBusStop = "서울역",
            busStopId = "100100001",
            arsId = "100001",
            longitude = null,
            latitude = null,
        ),
        onStationCardClick = {_, _ -> },
        onFavoriteIconClick = {}
    )
}