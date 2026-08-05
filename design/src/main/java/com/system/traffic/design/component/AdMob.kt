package com.system.traffic.design.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

data class AdConfig(
    val adUnitId: String = ""
)

// Compose 트리에서 전역으로 읽을 수 있는 CompositionLocal 키 생성
// (값이 바뀔 일이 거의 없으므로 staticCompositionLocalOf 사용 시 Recomposition 최적화됨)
val LocalAdConfig = staticCompositionLocalOf { AdConfig() }

@Composable
fun AdBannerView(
    modifier: Modifier = Modifier,
    adUnitId: String = LocalAdConfig.current.adUnitId, // 기본값으로 LocalAdConfig에 주입된 adUnitId를 자동으로 가져옴
) {
    // ID가 비어있을 땐 Modifier 패딩 공간조차 차치하지 않도록 완전 미출력
    if (adUnitId.isNotBlank()) {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    this.adUnitId = adUnitId
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}