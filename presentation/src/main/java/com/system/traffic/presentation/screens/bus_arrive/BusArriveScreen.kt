package com.system.traffic.presentation.screens.bus_arrive

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.system.traffic.design.R
import com.system.traffic.design.component.AdBannerView
import com.system.traffic.design.ui.theme.TrafficTheme
import com.system.traffic.presentation.PresentationConstants.DEFAULT_LATITUDE
import com.system.traffic.presentation.PresentationConstants.DEFAULT_LONGITUDE
import com.system.traffic.presentation.firebase.ScreenName
import com.system.traffic.presentation.firebase.TrackScreenView
import com.system.traffic.presentation.screens.bus_arrive.action.BusArriveAction
import com.system.traffic.presentation.screens.bus_arrive.component.BusArriveSection
import com.system.traffic.presentation.screens.bus_arrive.component.BusStopAndFavoriteSection
import com.system.traffic.presentation.screens.bus_arrive.component.NextBusStopSection
import com.system.traffic.presentation.screens.bus_arrive.component.RefreshButton
import com.system.traffic.presentation.screens.bus_arrive.state.BusArriveState
import com.system.traffic.presentation.screens.bus_arrive.viewmodel.BusArriveViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun BusArriveScreenRoute(
    arsId: String,
    busStopId: String,
    busArriveViewModel: BusArriveViewModel = hiltViewModel(),
    onClickBusArriveCard: (String) -> Unit,
) {
    TrackScreenView(screenName = ScreenName.BusArrive)

    val state by busArriveViewModel.state.collectAsStateWithLifecycle()

    // 화면 진입: 정류장 정보 및 도착 정보 로딩 (OnResume보다 먼저 선언해 busStopId 저장 순서 보장)
    LaunchedEffect(busArriveViewModel, arsId, busStopId) {
        busArriveViewModel.onAction(BusArriveAction.OnEnter(arsId = arsId, busStopId = busStopId))
    }

    // 화면 resume/pause 시 도착 정보 갱신 시작/중지
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        busArriveViewModel.onAction(BusArriveAction.OnResume)
    }
    LifecycleEventEffect(event = Lifecycle.Event.ON_PAUSE) {
        busArriveViewModel.onAction(BusArriveAction.OnPause)
    }

    BusArriveScreen(
        state = state,
        remainingSeconds = busArriveViewModel.remainingSeconds,
        onAction = busArriveViewModel::onAction,
        onClickBusArriveCard = onClickBusArriveCard,
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun BusArriveScreen(
    state: BusArriveState,
    remainingSeconds: StateFlow<Int>,
    onAction: (BusArriveAction) -> Unit,
    onClickBusArriveCard: (String) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Expanded // 진입 시 자동으로 올라옴
        ),
    )

    val currentStationLatitude = state.stationInfo.latitude?.toDoubleOrNull() ?: DEFAULT_LATITUDE
    val currentStationLongitude = state.stationInfo.longitude?.toDoubleOrNull() ?: DEFAULT_LONGITUDE

    val currentLocation = LatLng(currentStationLatitude, currentStationLongitude)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState()

    LaunchedEffect(key1 = currentLocation) {
        cameraPositionState.position = CameraPosition(currentLocation, 14.0) // zoom 이 커질수록 확대됨
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
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
                        .fillMaxHeight(0.8f) // 최대 확장 시 화면의 80%까지 올라옴
                ) {
                    AdBannerView(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .padding(bottom = 20.dp)
                        ,
                    )

                    BusStopAndFavoriteSection(
                        busStopName = state.stationInfo.busStopName ?: "",
                        selected = state.stationInfo.selected,
                        onClickFavorite = { onAction(BusArriveAction.OnClickFavoriteIcon(stationModel = state.stationInfo))}
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "ID : ${state.stationInfo.arsId}",
                        style = TrafficTheme.typography.busArriveBody,
                        modifier = Modifier
                            .padding(start = 20.dp)
                        ,
                        color = TrafficTheme.colors.textPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NextBusStopSection(
                        nextBusStopName = state.stationInfo.nextBusStop ?: "",
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = TrafficTheme.colors.divider
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BusArriveSection(
                        isLoading = state.isLoading,
                        busArriveList = state.arriveList,
                        onClickBusArriveCard = onClickBusArriveCard,
                        onClickPinned = { lineId, isPinned -> onAction(BusArriveAction.OnClickPinnedIcon(lineId = lineId, isPinned = isPinned)) },
                    )
                }
            },
        ) { paddingValues ->
            NaverMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                ,
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = MarkerState(position = currentLocation),
                    captionText = state.stationInfo.busStopName ?: "",
                )
                // 1. 단일 마커 이미지 캐싱
                val busIcon = remember {
                    OverlayImage.fromResource(R.drawable.icon_bus_marker)
                }
                // 2. [개선] associate 를 활용해 임시 리스트를 생성하지 않고 다이렉트로 Map 생성
                val offsetCoordinates = remember(key1 = state.arriveList) {
                    val counts = mutableMapOf<LatLng, Int>()
                    state.arriveList
                        .filter { it.busLatitude != null && it.busLongitude != null }
                        .associate { bus ->
                            val baseLat = bus.busLatitude!!
                            val baseLng = bus.busLongitude!!
                            val baseLatLng = LatLng(baseLat, baseLng)
                            val count = counts[baseLatLng] ?: 0
                            counts[baseLatLng] = count + 1
                            val offsetLat = baseLat + (count * 0.00008)
                            val offsetLng = baseLng + (count * 0.00008)
                            // busId와 최종 타겟 좌표를 1:1로 매핑하여 리턴
                            bus.busId to LatLng(offsetLat, offsetLng)
                        }
                }
                // 3. [개선] 버스 리스트 순회부 - 컴포저블 분리로 완전히 쾌적해진 부분!
                state.arriveList.forEach { bus ->
                    val targetLatLng = offsetCoordinates[bus.busId]
                    if (targetLatLng != null) {
                        MovingBusMarker(
                            busId = bus.busId.orEmpty(),
                            lineColor = bus.lineColor,
                            targetLatLng = targetLatLng,
                            busIcon = busIcon,
                            lineName = bus.lineName.orEmpty(),
                            busStopName = bus.busStopName.orEmpty()
                        )
                    }
                }
            }
        }

        CountdownRefreshButton(
            modifier = Modifier
                .align (Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp)
            ,
            remainingSecondsFlow = remainingSeconds,
            onClickRefresh = { onAction(BusArriveAction.OnClickRefresh) }
        )
    }
}

/**
 * 카운트다운 flow를 여기서만 구독해 매초 recomposition 범위를 이 컴포저블로 한정
 */
@Composable
private fun CountdownRefreshButton(
    modifier: Modifier,
    remainingSecondsFlow: StateFlow<Int>,
    onClickRefresh: () -> Unit,
) {
    val remainingSeconds by remainingSecondsFlow.collectAsStateWithLifecycle()

    RefreshButton(
        modifier = modifier,
        remainingSeconds = remainingSeconds,
        onClickRefresh = onClickRefresh,
    )
}

/**
 * 실시간 좌표 갱신에 따라 부드럽게 이동하는 버스 마커 컴포저블
 */
@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MovingBusMarker(
    busId: String,
    lineColor: Color,
    targetLatLng: LatLng,
    busIcon: OverlayImage,
    lineName: String,
    busStopName: String,
) {
    key(busId) {
        // 1. 애니메이션 수치값 설정
        val animatedLat = remember { Animatable(targetLatLng.latitude.toFloat()) }
        val animatedLng = remember { Animatable(targetLatLng.longitude.toFloat()) }
        // 2. 타겟 좌표가 갱신될 때마다 800ms 동안 애니메이션 구동
        LaunchedEffect(targetLatLng) {
            val animationSpec = TweenSpec<Float>(durationMillis = 800)
            launch {
                animatedLat.animateTo(targetLatLng.latitude.toFloat(), animationSpec)
            }
            launch {
                animatedLng.animateTo(targetLatLng.longitude.toFloat(), animationSpec)
            }
        }
        // 3. 고정된 마커 상태 객체 보존
        val markerState = remember { MarkerState(position = targetLatLng) }
        // 4. 애니메이션 변화를 런타임에 동기화
        LaunchedEffect(animatedLat.value, animatedLng.value) {
            markerState.position = LatLng(
                animatedLat.value.toDouble(),
                animatedLng.value.toDouble()
            )
        }

        Marker(
            state = markerState,
            icon = busIcon,
            width = 80.dp,
            height = 80.dp,
            captionText = lineName,
            subCaptionText = busStopName,
            captionColor = lineColor,
            captionHaloColor = Color.White, // 도로 위에 글자가 묻히지 않도록 흰색 아웃라인 추가
            captionTextSize = 13.sp,
            subCaptionColor = Color(0xFF64748B),
            subCaptionHaloColor = Color.White,
            subCaptionTextSize = 10.sp,
        )
    }
}