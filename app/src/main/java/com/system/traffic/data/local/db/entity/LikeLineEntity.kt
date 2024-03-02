package com.system.traffic.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.system.traffic.common.Constants.LINE_LIKE_ENTITY
import com.system.traffic.domain.model.LineModel
import javax.annotation.Nonnull

@Entity(tableName = LINE_LIKE_ENTITY)
data class LikeLineEntity(

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
)

fun LineModel.toLikeLineEntity(): LikeLineEntity {
    return LikeLineEntity(
        dir_down_name = dir_down_name,
        run_interval = run_interval,
        last_run_time = last_run_time,
        line_num = line_num,
        first_run_time = first_run_time,
        dir_up_name = dir_up_name,
        line_id = line_id!!,
        line_kind = line_kind,
        line_name = line_name,
    )
}

fun LikeLineEntity.toLikeLineModel(): LineModel {
    return LineModel(
        dir_down_name = dir_down_name!!,
        run_interval = run_interval!!,
        last_run_time = last_run_time!!,
        line_num = line_num!!,
        first_run_time = first_run_time!!,
        dir_up_name = dir_up_name!!,
        line_id = line_id,
        line_kind = line_kind!!,
        line_name = line_name!!,
    )
}