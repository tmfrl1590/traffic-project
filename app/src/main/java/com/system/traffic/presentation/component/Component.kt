package com.system.traffic.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.system.traffic.data.local.db.entity.StationEntity
import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.model.StationModel
import com.system.traffic.navigation.Graph
import com.system.traffic.presentation.screen.CommonViewModel
import com.system.traffic.presentation.screen.bus_arrive.lineColor
import com.system.traffic.presentation.screen.line.LineViewModel
import com.system.traffic.presentation.screen.station.StationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationInfo(
    stationModel: StationModel,
    stationViewModel: StationViewModel,
    navHostController: NavHostController,
){
    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    var selectedStation by remember {
        mutableStateOf(false)
    }

    selectedStation = likeStationList.contains(stationModel)

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            navHostController.navigate(route = "${Graph.BUS_ARRIVE}/${stationModel.busstop_id}")
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            IconButton(
                onClick = { if(selectedStation){
                    stationViewModel.deleteLikeStation(stationModel.busstop_id)
                }else{
                    stationViewModel.insertLikeStation(stationModel)
                } },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ){
                Icon(
                    imageVector = if(selectedStation) Icons.Default.Favorite else Icons.Default.FavoriteBorder ,
                    contentDescription = "Favorite"
                )
            }

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stationModel.busstop_name!!,
                    modifier = Modifier
                        .height(50.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${stationModel.next_busstop} | ${stationModel.ars_id}",
                    modifier = Modifier
                        .weight(5f)
                )
            }
        }
    }
}

@Composable
fun LineInfo(
    //stationEntity: StationEntity,
    lineModel: LineModel,
    stationViewModel: StationViewModel,
    lineViewModel: LineViewModel,
    //commonViewModel: CommonViewModel,
    navHostController: NavHostController
){

    //val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())
    val likeLineList by lineViewModel.likeLineList.collectAsState()

    var selectedStation by remember {
        mutableStateOf(false)
    }

    selectedStation = likeLineList.contains(lineModel)

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(12.dp),
        //onClick = { commonViewModel.goBusArriveScreen(navHostController, stationEntity.busstop_id.toString()) }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            IconButton(
                onClick = {
                    if(selectedStation)lineViewModel.deleteLikeLine(lineModel.line_id!!)
                    else lineViewModel.insertLikeLine(lineModel.line_id!!)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ){
                Icon( if(selectedStation) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, "")
            }

            val a = lineModel.line_kind?.let { lineColor(it) }

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = lineModel.line_name!!,
                    modifier = Modifier.height(50.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = a!!
                )

                Text(
                    text = "${lineModel.dir_down_name} ~ ${lineModel.dir_up_name}",
                    modifier = Modifier.weight(5f)
                )
            }
        }
    }
}