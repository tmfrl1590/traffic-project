package com.system.traffic.presentation.screen.station

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.system.traffic.presentation.component.SearchBox
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.navigation.BusArriveNav
import com.system.traffic.navigation.Graph
import com.system.traffic.presentation.screen.CommonViewModel
import com.system.traffic.presentation.screen.line.LineViewModel


@Composable
fun StationScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
    lineViewModel: LineViewModel = hiltViewModel(),
    commonViewModel: CommonViewModel = hiltViewModel(),
){
    var keyword by remember { mutableStateOf("") }

    val searchedStationList by stationViewModel.searchResult.collectAsState(initial = listOf())

    Column {
        SearchBox(
            type = "정류장",
            keyword = keyword,
            onValueChange = {keyword = it},
            searchAction = {stationViewModel.getSearchedStationList(keyword = keyword) },
        )

        if(searchedStationList.isNotEmpty()){
            LazyColumn(){
                items(searchedStationList.size){ index ->
                    StationInfo(
                        searchedStationList[index],
                        stationViewModel,
                        commonViewModel,
                        navHostController
                    )
                }
            }
        }else{
            //Log.i("qewqwe", "값이없습니다ㅑ")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationInfo(
    stationEntity: StationEntity,
    stationViewModel: StationViewModel,
    commonViewModel: CommonViewModel,
    navHostController: NavHostController
){
    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    Card(
        modifier = Modifier
            .padding(12.dp)
            .height(100.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
        onClick = {
            //commonViewModel.goBusArriveScreen(navHostController, stationEntity.busstop_id.toString())
            //navHostController.navigate(BusArriveNav.navigateWithArg(stationEntity.busstop_id.toString()))
            println("test111 ${stationEntity.busstop_id.toString()}")
            navHostController.navigate(route = "${Graph.BUS_ARRIVE}/${stationEntity.busstop_id.toString()}")
        }
    ){
        Box(modifier = Modifier.fillMaxSize()){
            IconButton(
                onClick = { stationViewModel.updateStation(stationEntity) },
                modifier = Modifier.align(Alignment.TopEnd)
            ){
                Icon( if(likeStationList.contains(stationEntity)) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, "")
            }

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(text = stationEntity.busstop_name!!,  modifier = Modifier.height(50.dp), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "${stationEntity.next_busstop} | ${stationEntity.ars_id}", modifier = Modifier.weight(5f))
              }
        }
    }
}