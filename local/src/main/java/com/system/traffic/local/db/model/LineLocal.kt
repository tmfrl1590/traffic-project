package com.system.traffic.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.system.traffic.local.LocalMapper
import com.system.traffic.local.RoomConstant
import com.traffic.data.model.local.LineEntity
import javax.annotation.Nonnull

@Entity(tableName = RoomConstant.Table.LINE_LOCAL)
data class LineLocal (
    @PrimaryKey
    @Nonnull
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
): LocalMapper<LineEntity>{
    override fun toData(): LineEntity = LineEntity(
        lineId = line_id,
        dirDownName = dir_down_name,
        runInterval = run_interval,
        lastRunTime = last_run_time,
        lineNum = line_num,
        firstRunTime = first_run_time,
        dirUpName = dir_up_name,
        lineKind = line_kind,
        lineName = line_name,
        selected = selected,
    )
}