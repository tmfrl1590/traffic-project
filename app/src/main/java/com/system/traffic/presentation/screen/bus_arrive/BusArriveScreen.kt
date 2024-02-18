package com.system.traffic.presentation.screen.bus_arrive

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.system.traffic.domain.dataModel.BusArriveModel
import com.system.traffic.presentation.screen.line.LineViewModel
import com.system.traffic.presentation.screen.station.StationViewModel

@Composable
fun BusArriveScreen(
    arsId: String,
    busArriveViewModel: BusArriveViewModel = hiltViewModel(),
    stationViewModel: StationViewModel = hiltViewModel(),
    lineViewModel: LineViewModel = hiltViewModel(),
) {

    //val busArriveList by busArriveViewModel.resultBusArriveList.observeAsState()

    LaunchedEffect(key1 = arsId){
        //busArriveViewModel.getBusArrive(arsId)
    }

    /*LaunchedEffect(Unit){
        lineViewModel.getLineColor()
    }*/

    LaunchedEffect(key1 = arsId){
        stationViewModel.getStationInfo(arsId )
    }

    Column {
        TopInfoArea(stationViewModel)

        Spacer(modifier = Modifier.size(20.dp))

        /*if(busArriveList != null){
            LazyColumn(

            ){
                itemsIndexed(
                    items = busArriveList!!.itemList,
                    key = null,
                ){ index, item ->
                    BusArriveCard(item, lineViewModel)
                }

            }
        }else{
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "버스 도착정보가 없습니다.", fontSize = 32.sp, textAlign = TextAlign.Center)
            }
        }*/
    }
}

@Composable
fun TopInfoArea(stationViewModel: StationViewModel){
    val stationInfo by stationViewModel.stationInfo.collectAsState()


    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "${stationInfo.busstop_name.toString()}(${stationInfo.ars_id})", fontSize = 24.sp)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "${stationInfo.next_busstop.toString()}방면", fontSize = 16.sp)
        Spacer(modifier = Modifier.size(8.dp))
        Row {
            if(stationInfo.selected){
                IconButton(onClick = { stationViewModel.updateStation(stationInfo) }) {
                    Icon(Icons.Default.Favorite, "")
                }
            } else{
                IconButton(onClick = { stationViewModel.updateStation(stationInfo) }) {
                    Icon(Icons.Default.FavoriteBorder, "")
                }
            }

            Spacer(modifier = Modifier.size(12.dp))

            IconButton(onClick = {  }) {
                Icon(Icons.Default.Refresh, "")
            }
        }
    }
}

@Composable
fun BusArriveCard(busArriveModel: BusArriveModel, lineViewModel: LineViewModel){

    val lineColorList by lineViewModel.lineColorList.collectAsState()

    var color = Color.Black

    for(i in lineColorList){
        if(i.line_id == busArriveModel.line_id){
            color = lineColor(i.line_kind.toString())
        }
    }

    Card(
        modifier = Modifier
            .padding(12.dp)
            .height(100.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, shape = RoundedCornerShape(5.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            IconButton(
                onClick = { },
                modifier = Modifier.align(Alignment.TopEnd)
            ){
                Icon(Icons.Filled.Favorite, "")
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(text = busArriveModel.line_name!!, fontSize = 16.sp, modifier = Modifier.height(40.dp), color = color)
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ){
                    Text(text = "현재 : ${busArriveModel.busstop_name} (${busArriveModel.remain_stop} 정거장 전)", modifier = Modifier.wrapContentWidth())
                    Text(text = "${busArriveModel.remain_min}분", modifier = Modifier.wrapContentWidth(), textAlign = TextAlign.End, color = Color.Red)
                }
            }
        }
    }
}

fun lineColor(lien_kind: String): Color {
    return when (lien_kind) {
        "1" -> {
            Color.Red
        }

        "2" -> {
            Color.Green
        }

        "3" -> {
            Color.Blue
        }

        else -> {
            Color.Black
        }
    }
}