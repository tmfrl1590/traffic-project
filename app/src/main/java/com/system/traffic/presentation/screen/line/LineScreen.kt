package com.system.traffic.presentation.screen.line

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.system.traffic.data.local.db.entity.StationEntity
import com.system.traffic.presentation.screen.CommonViewModel
import com.system.traffic.presentation.screen.station.StationViewModel

@Composable
fun LineScreen(
    navHostController: NavHostController,
    stationViewModel: StationViewModel = hiltViewModel(),
    lineViewModel: LineViewModel = hiltViewModel(),
    commonViewModel: CommonViewModel = hiltViewModel(),
){
    var keyword by remember { mutableStateOf("") }

    val searchedStationList by stationViewModel.searchResult.collectAsState(initial = listOf())

    Column {
        SearchBox(
            type = "노선",
            keyword = keyword,
            onValueChange = {keyword = it},
            searchAction = {stationViewModel.getSearchedStationList(keyword = keyword) },
        )

        if(searchedStationList.isNotEmpty()){
            LazyColumn(){
                items(searchedStationList.size){ index ->
                    //LineInfo(searchedStationList[index], stationViewModel, commonViewModel, navHostController)
                }
            }
        }else{
            //Log.i("qewqwe", "값이없습니다ㅑ")
        }
    }
}

