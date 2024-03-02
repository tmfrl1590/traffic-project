package com.system.traffic.presentation.screen.bus_arrive

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.system.traffic.common.Resource
import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.model.BusArriveModel
import com.system.traffic.domain.model.LineModel
import com.system.traffic.presentation.screen.line.LineViewModel
import com.system.traffic.presentation.screen.station.StationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusArriveScreen(
    arsId: String,
    navController: NavController,
    busArriveViewModel: BusArriveViewModel = hiltViewModel(),
    stationViewModel: StationViewModel = hiltViewModel(),
    lineViewModel: LineViewModel = hiltViewModel(),
) {

    // arsId 에 해당하는 정류장 정보 가져오기
    LaunchedEffect(key1 = arsId){
        stationViewModel.getStationInfo(arsId)
    }

    LaunchedEffect(Unit){
        stationViewModel.getLikeStationList()
        lineViewModel.getLikeLineList1()
    }

    val stationInfo by stationViewModel.stationInfo.collectAsState()
    val likeStationList by stationViewModel.likeStationList.collectAsState(initial = listOf())

    var selectedStation by remember {
        mutableStateOf(false)
    }

    selectedStation = likeStationList.contains(stationInfo)

    /*LaunchedEffect(Unit){
        lineViewModel.getLineColor()
    }*/

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "${stationInfo.busstop_name} (${stationInfo.ars_id})",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if(selectedStation){
                                stationViewModel.deleteLikeStation(arsId)
                            }else{
                                stationViewModel.insertLikeStation(stationInfo)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if(selectedStation) Icons.Default.Favorite else Icons.Default.FavoriteBorder ,
                            contentDescription = "Favorite"
                        )
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            NextBusStop(
                nextBusStopName = stationInfo.next_busstop
            )

            Spacer(modifier = Modifier.size(20.dp))

            BusArriveList(
                arsId = arsId,
                busArriveViewModel = busArriveViewModel,
                lineViewModel = lineViewModel
            )
        }
    }
}

@Composable
fun BusArriveList(
    arsId: String,
    busArriveViewModel: BusArriveViewModel,
    lineViewModel: LineViewModel
){
    val likeLineList by lineViewModel.likeLineList.collectAsState(initial = listOf())


    val busArriveList = produceState<Resource<BusArriveBody>>(initialValue = Resource.Loading()){
        value = busArriveViewModel.getBusArriveList(arsId)
    }.value

    if(busArriveList.data == null){
        CircularProgressIndicator()
    }else{
        if(busArriveList.data.itemList.isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "버스도착 정보가\n없습니다",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                )
            }
        }else{
            LazyColumn {
                itemsIndexed(
                    items = busArriveList.data.itemList,
                    key = { index, item ->
                        item.bus_id!!
                    }
                ){index, item ->
                    BusArriveCard(
                        busArriveModel = item,
                        lineViewModel = lineViewModel,
                        likeLineList = likeLineList,
                        onAdd = {
                            lineViewModel.insertLikeLine(it)
                        },
                        onRemove = {
                            lineViewModel.deleteLikeLine(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NextBusStop(
    nextBusStopName: String,
){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "${nextBusStopName}방면",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BusArriveCard(
    busArriveModel: BusArriveModel,
    lineViewModel: LineViewModel,
    likeLineList: List<LineModel>,
    onAdd: (String) -> Unit ,
    onRemove: (String) -> Unit
){
    LaunchedEffect(true){
        //lineViewModel.getLineOne(busArriveModel.line_id!!)
    }

    println("likeLineList : $likeLineList")

    val lineColorList by lineViewModel.lineColorList.collectAsState()

    //val lineModelOne by lineViewModel.lineInfo.collectAsState()
    //println("즐겨찾기11 : $lineModelOne")

    var color = Color.Black


    /*for(i in lineColorList){
        if(i.line_id == busArriveModel.line_id){
            color = lineColor(i.line_kind.toString())
        }
    }*/

    var selectedLine by remember {
        mutableStateOf(false)
    }

    likeLineList.forEach {
        if(busArriveModel.line_id == it.line_id){
            selectedLine = true
        }
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            IconButton(
                onClick = {
                    if(selectedLine){
                        onRemove(busArriveModel.line_id!!)
                        selectedLine = false
                    }else{
                        onAdd(busArriveModel.line_id!!)
                    }
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ){
                Icon(
                    imageVector = if(selectedLine) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = busArriveModel.line_name!!,
                    fontSize = 16.sp,
                    modifier = Modifier.height(40.dp),
                    color = color
                )

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

fun lineColor(lienKind: String): Color {
    return when (lienKind) {
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