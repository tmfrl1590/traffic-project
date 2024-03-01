package com.system.traffic.presentation.screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.navigation.Graph
import com.system.traffic.presentation.screen.station.StationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationInfo(
    stationEntity: StationEntity,
    stationViewModel: StationViewModel,
    navHostController: NavHostController
){
    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            navHostController.navigate(route = "${Graph.BUS_ARRIVE}/${stationEntity.busstop_id.toString()}")
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            IconButton(
                onClick = { stationViewModel.updateStation(stationEntity) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ){
                Icon( if(likeStationList.contains(stationEntity)) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, "")
            }

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stationEntity.busstop_name!!,
                    modifier = Modifier
                        .height(50.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${stationEntity.next_busstop} | ${stationEntity.ars_id}",
                    modifier = Modifier
                        .weight(5f)
                )
            }
        }
    }
}