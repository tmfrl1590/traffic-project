package com.system.traffic.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.system.traffic.local.LocalConstants.LINE_LOCAL
import com.traffic.data.model.local.LineEntity
import javax.annotation.Nonnull

@Entity(tableName = LINE_LOCAL)
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
)

fun LineEntity.toLineEntity(): LineLocal {
    return LineLocal(
        dir_down_name = dir_down_name!!,
        run_interval = run_interval,
        last_run_time = last_run_time,
        line_num = line_num,
        first_run_time = first_run_time,
        dir_up_name = dir_up_name,
        line_id = line_id!!,
        line_kind = line_kind,
        line_name = line_name,
        selected = false ,
    )
}

fun LineLocal.toLineModel(): LineEntity {
    return LineEntity(
        dir_down_name = dir_down_name,
        run_interval = run_interval,
        last_run_time = last_run_time,
        line_num = line_num,
        first_run_time = first_run_time,
        dir_up_name = dir_up_name,
        line_id = line_id,
        line_kind = line_kind,
        line_name = line_name,
        selected = selected,
    )
}