package com.traffic.data.model.local

import com.traffic.domain.model.LineModel

data class LineEntity(
    val line_id : String,
    val dir_down_name : String?,
    val run_interval : String?,
    val last_run_time : String?,
    val line_num : String?,
    val first_run_time : String?,
    val dir_up_name : String?,
    val line_kind : String?,
    val line_name : String?,
    var selected : Boolean
)

fun LineModel.toEntity() = LineEntity(
    line_id = lineId ?: "",
    dir_down_name = dirDownName,
    run_interval = runInterval,
    last_run_time = lastRunTime,
    line_num = lineNum,
    first_run_time = firstRunTime,
    dir_up_name = dirUpName,
    line_kind = lineKind,
    line_name = lineName,
    selected = false
)

fun LineEntity.toModel() = LineModel(
    lineId = line_id,
    dirDownName = dir_down_name,
    runInterval = run_interval,
    lastRunTime = last_run_time,
    lineNum = line_num,
    firstRunTime = first_run_time,
    dirUpName = dir_up_name,
    lineKind = line_kind,
    lineName = line_name
)
