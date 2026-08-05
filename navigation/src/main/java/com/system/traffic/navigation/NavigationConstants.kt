package com.system.traffic.navigation

object NavigationConstants {

    /** 홈 탭에서 뒤로가기 두 번으로 앱 종료 시 허용 간격(ms) */
    const val EXIT_BACK_PRESS_INTERVAL_MS = 2_000L

    /** 동일 뒤로가기 이벤트가 중복 전달될 때 종료로 오인하지 않기 위한 최소 간격(ms) */
    const val EXIT_BACK_PRESS_MIN_INTERVAL_MS = 300L

}