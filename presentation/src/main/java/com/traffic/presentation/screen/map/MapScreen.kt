package com.traffic.presentation.screen.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.Gravity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationOverlay
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.traffic.presentation.screen.component.snackBarMessage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
) {
    // 현재 위치 좌표
    var currentLocation by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    val locationRequest = LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 1000).apply {
        setIntervalMillis(1000)
        setPriority(PRIORITY_HIGH_ACCURACY)
    }.build()

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            // 카메라 초기 위치를 현재위치로 설정
            val fusedLocationProvideClient = LocationServices.getFusedLocationProviderClient(context)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            fusedLocationProvideClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    // 현재 위치 세팅
                    currentLocation = LatLng(location.latitude, location.longitude)

                    // 현재 위치로 카메라 이동
                    val lat = location.latitude
                    val lng = location.longitude
                    position = CameraPosition(LatLng(lat, lng), 18.0)
                    //this.move(CameraUpdate.scrollTo(LatLng(lat, lng)))
                }
        }
        task.addOnFailureListener { exception ->
            snackBarMessage(snackBarHostState, "GPS를 켜주세요")
        }
    }

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.Basic,
                maxZoom = 20.0,
                minZoom = 5.0,
                locationTrackingMode = LocationTrackingMode.Follow,
            )
        )
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isScaleBarEnabled = true, // 축척 막대기 보이기 true
                isLocationButtonEnabled = true, // 좌측 하단 현재 위치 버튼 보이기 true
                logoGravity = Gravity.BOTTOM or Gravity.END,
                isLogoClickEnabled = false, // 네이버 로고 클릭 false
                isRotateGesturesEnabled = true, // 회전 제스처 true
            )
        )
    }

    NaverMap(
        locationSource = rememberFusedLocationSource(isCompassEnabled = true),
        properties = mapProperties,
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState,
    ) {
        // 현재 위치 표시
        LocationOverlay(position = currentLocation)
    }
}