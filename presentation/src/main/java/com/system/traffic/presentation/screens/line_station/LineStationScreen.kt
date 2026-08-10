package com.system.traffic.presentation.screens.line_station

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.system.traffic.core.enums.LineType
import com.system.traffic.design.R
import com.system.traffic.design.ui.theme.LocalSnackBarHostState
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.presentation.PresentationConstants.DEFAULT_LATITUDE
import com.system.traffic.presentation.PresentationConstants.DEFAULT_LONGITUDE
import com.system.traffic.presentation.firebase.ScreenName
import com.system.traffic.presentation.firebase.TrackScreenView
import com.system.traffic.presentation.screens.line_station.action.LineStationAction
import com.system.traffic.presentation.screens.line_station.component.BusDirectionTabSection
import com.system.traffic.presentation.screens.line_station.component.LineStationListSection
import com.system.traffic.presentation.screens.line_station.state.LineStationState
import com.system.traffic.presentation.screens.line_station.viewmodel.LineStationViewModel
import kotlinx.coroutines.launch

@Composable
fun LineStationScreenRoute(
    lineId: String,
    lineStationViewModel: LineStationViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = ScreenName.LineStation)

    val snackBarHostState = LocalSnackBarHostState.current

    val state by lineStationViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        lineStationViewModel.getLineStationList(lineId = lineId)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            lineStationViewModel.errorFlow.collect { message ->
                snackBarHostState.showSnackbar(message = message)
            }
        }
    }

    LineStationScreen(
        state = state,
        onAction = lineStationViewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
private fun LineStationScreen(
    state: LineStationState,
    onAction: (LineStationAction) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val turnStationSeq = remember(state.lineStationList) {
        state.lineStationList.firstOrNull { it.lineType == LineType.TURN }?.seq ?: -1
    }

    val upLineList = remember(state.lineStationList, turnStationSeq) {
        if (turnStationSeq != -1) {
            state.lineStationList.filter { it.seq <= turnStationSeq }
        } else {
            state.lineStationList
        }
    }
    val downLineList = remember(state.lineStationList, turnStationSeq) {
        if (turnStationSeq != -1) {
            state.lineStationList.filter { it.seq > turnStationSeq }
        } else {
            emptyList()
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE), 14.0)
    }

    LaunchedEffect(key1 = state.selectedBusDirectionIndex, key2 = state.lineStationList) {
        val targetList = if (state.selectedBusDirectionIndex == 0) upLineList else downLineList
        targetList.firstOrNull()?.let { firstStation ->
            cameraPositionState.animate(
                update = CameraUpdate.scrollTo(
                    LatLng(firstStation.latitude, firstStation.longitude)
                )
            )
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded // 진입 시 자동으로 올라옴
        ),
    )

    BottomSheetScaffold(
        sheetContainerColor = TrafficTheme.colors.mainBackground,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp, // 하단 최소 노출 높이
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle()
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(horizontal = 16.dp)
            ) {
                val firstStation = state.lineStationList.firstOrNull()
                val lineName = firstStation?.lineName ?: stringResource(R.string.line_info)
                
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = lineName,
                    style = TrafficTheme.typography.busArriveTitle,
                    color = TrafficTheme.colors.textPrimary
                )

                // 상행, 하행 선택 탭
                BusDirectionTabSection(
                    selectedBusDirectionIndex = state.selectedBusDirectionIndex,
                    onClickBusDirectionTab = { onAction(LineStationAction.OnClickBusDirectionTab(selectedBusDirectionIndex = it))}
                )

                Spacer(modifier = Modifier.height(8.dp))

                val currentList = if (state.selectedBusDirectionIndex == 0) upLineList else downLineList

                if (currentList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.line_station_empty),
                            color = TrafficTheme.colors.textPrimary,
                            style = TrafficTheme.typography.empty
                        )
                    }
                } else {
                    LineStationListSection(
                        currentList = currentList,
                    )
                }
            }
        }
    ){ paddingValues ->
        if(state.lineStationList.isNotEmpty()){
            NaverMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                ,
                cameraPositionState = cameraPositionState,
                onMapClick = { _, _ ->
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            ) {
                // 1. 회차지 계산은 리스트가 바뀔 때만 수행 (recomposition마다 재계산 방지)
                val filterList = remember(state.lineStationList) {
                    val turnIndex = state.lineStationList.indexOfFirst { it.lineType == LineType.TURN }
                    if (turnIndex != -1) {
                        state.lineStationList.subList(0, turnIndex + 1)
                    } else {
                        state.lineStationList
                    }
                }
                // 2. 각 정류장을 순회하며 지도 위에 마커를 표시합니다.
                filterList.forEach { station ->
                    // Compose가 각각의 마커를 식별하여 불필요한 재렌더링을 피하도록 key를 지정합니다.
                    key(station.busStopId) {
                        // 좌표는 station 데이터에서 파생되는 값이라 Bundle 저장(rememberSaveable) 불필요
                        val markerState = remember(station.latitude, station.longitude) {
                            MarkerState(
                                position = LatLng(station.latitude, station.longitude)
                            )
                        }
                        // 정류소 타입에 따라 마커의 색상을 다르게 지정합니다.
                        val markerColor = when (station.lineType) {
                            LineType.START -> Color.Green      // 기점 (초록색)
                            LineType.TURN -> Color.Red         // 회차지 (빨간색)
                            else -> Color(0xFF1E88E5)          // 일반 정류장 (파란색)
                        }
                        Marker(
                            state = markerState,
                            captionText = station.busStopName,
                            iconTintColor = markerColor,
                            captionMinZoom = 12.0,
                            width = 30.dp,
                            height = 40.dp
                        )
                    }
                }
            }
        }
    }
}