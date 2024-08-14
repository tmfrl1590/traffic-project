package com.traffic.domain.model

data class LineModel(
    val dirDownName: String?,
    val runInterval: String?,
    val lastRunTime: String?,
    val lineNum: String?,
    val firstRunTime: String?,
    val dirUpName: String?,
    val lineId: String?,
    val lineKind: String?,
    val lineName: String?,
)