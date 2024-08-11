package com.traffic.presentation.component

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
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
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseSyncProgressIndicator
import com.silver.navigation.Screens
import com.traffic.presentation.screen.station.StationViewModel
import com.traffic.presentation.ui.theme.MainColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StationInfo(
    stationModel: com.traffic.domain.model.StationModel,
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
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            navHostController.navigate(Screens.BusArrive(arsId = stationModel.busstop_id ?: ""))
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            IconButton(
                onClick = { if(selectedStation){
                    stationViewModel.deleteLikeStation(stationModel.busstop_id ?: "")
                }else{
                    stationViewModel.insertLikeStation(stationModel)
                } },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ){
                Icon(
                    imageVector = if(selectedStation) Icons.Default.Favorite else Icons.Default.FavoriteBorder ,
                    contentDescription = "Favorite",
                    tint = MainColor
                )
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stationModel.busstop_name ?: "",
                    modifier = Modifier
                        .height(52.dp),
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
    lineModel: com.traffic.domain.model.LineModel,
){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(100.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(12.dp),
    ){
        val lineColor = lineModel.line_kind?.let { lineColor(it) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ){
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = lineModel.line_name!!,
                    modifier = Modifier.height(52.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = lineColor!!
                )

                Text(
                    text = "${lineModel.dir_down_name} ~ ${lineModel.dir_up_name}",
                    modifier = Modifier.weight(5f)
                )
            }
        }
    }
}

@Composable
fun NoDataComponent(
    modifier: Modifier = Modifier,
    text: String,
){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Color.Black
        )
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

/*@Composable
fun BannersAds(
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                //adUnitId = "ca-app-pub-3940256099942544/6300978111" // 테스트
                adUnitId = "ca-app-pub-3991873148102758/7356139432" // 실
                loadAd(AdRequest.Builder().build())
            }
        },
        update = { adView ->
            adView.loadAd(AdRequest.Builder().build())
        }
    )
}*/

@Composable
fun TitleComponent(
    title: String,
){
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(16.dp),
        color = Color.Black
    )
}

@Composable
fun CustomLoadingBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        BallPulseSyncProgressIndicator(
            modifier = Modifier,
            color = Color.Gray,
            animationDuration = 800,
            animationDelay = 200,
            startDelay = 0,
            ballDiameter = 12.dp,
            ballJumpHeight = 42.dp,
            ballCount = 4
        )
    }
}

@Composable
fun ScaffoldBackIcon() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "back",
        tint = Color.Black
    )
}

fun snackBarMessage(
    snackBarHostState: SnackbarHostState,
    message: String,
    durationTime: Long = 2000L
) {
    CoroutineScope(Dispatchers.Main).launch {
        val job = launch {
            snackBarHostState.showSnackbar(message)
        }
        delay(durationTime)
        job.cancel()
    }
}