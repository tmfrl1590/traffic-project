package com.system.traffic.presentation.screens.bus_arrive.action

import com.system.traffic.domain.model.StationModel

sealed interface BusArriveAction {
    /** 화면 진입: 정류장 정보 및 도착 정보 최초 로딩 */
    data class OnEnter(val arsId: String, val busStopId: String) : BusArriveAction
    /** 화면 재개: 자동 새로고침 타이머 시작 */
    data object OnResume : BusArriveAction
    /** 화면 일시정지: 자동 새로고침 타이머 정지 */
    data object OnPause : BusArriveAction

    data class OnClickFavoriteIcon(val stationModel: StationModel) : BusArriveAction
    data object OnClickRefresh : BusArriveAction
    data class OnClickPinnedIcon(val lineId: String, val isPinned: Boolean) : BusArriveAction
}
