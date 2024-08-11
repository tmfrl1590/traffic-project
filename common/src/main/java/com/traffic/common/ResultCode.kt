package com.traffic.common

enum class ResultCode(
    val code: String,
    val message: String,
) {
    // BusArrive
    BUS_ARRIVE_GET_SUCCESS("A00001", "버스 도착 정보 조회 성공"),
    BUS_ARRIVE_GET_FAILURE("A00002", "버스 도착 정보 조회 실패"),

    // Common
    COMMON_EXCEPTION("A99999", "알 수 없는 오류가 발생했습니다."),
}