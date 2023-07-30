package com.system.traffic.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "line")
data class LineEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val dir_down_name : String?,
    val run_interval : String?,
    val last_run_time : String?,
    val line_num : String?,
    val first_run_time : String?,
    val dir_up_name : String?,
    val line_id : String?,
    val line_kind : String?,
    val line_name : String?,
    var selected : Boolean
    )