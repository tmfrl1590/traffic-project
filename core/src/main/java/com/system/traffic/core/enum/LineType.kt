package com.system.traffic.core.enum

enum class LineType {
    REGULAR,   // 1: 운행정류장
    START,     // 2: 시작기점
    TURN,      // 3: 종점 (회차지)
    TERMINAL;  // 4: 종료기점

    companion object {
        fun fromFlag(flag: Int): LineType {
            return when (flag) {
                2 -> START
                3 -> TURN
                4 -> TERMINAL
                else -> REGULAR
            }
        }
    }
}