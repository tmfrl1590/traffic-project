package com.traffic.data.model.local

import com.traffic.data.DataMapper
import com.traffic.domain.model.LineModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineEntity(
    @Serializable(with = AnyToStringSerializer::class)
    @SerialName("LINE_ID") val lineId : String,

    @SerialName("DIR_DOWN_NAME") val dirDownName : String?,

    @Serializable(with = NullableAnyToStringSerializer::class)
    @SerialName("RUN_INTERVAL") val runInterval : String?,

    @SerialName("LAST_RUN_TIME") val lastRunTime : String?,

    @Serializable(with = NullableAnyToStringSerializer::class)
    @SerialName("LINE_NUM") val lineNum : String?,

    @SerialName("FIRST_RUN_TIME") val firstRunTime : String?,
    @SerialName("DIR_UP_NAME") val dirUpName : String?,

    @Serializable(with = NullableAnyToStringSerializer::class)
    @SerialName("LINE_KIND") val lineKind : String?,
    @SerialName("LINE_NAME") val lineName : String?,
    var selected : Boolean = false
): DataMapper<LineModel>{
    override fun toDomain(): LineModel {
        return LineModel(
            lineId = lineId,
            dirDownName = dirDownName,
            runInterval = runInterval,
            lastRunTime = lastRunTime,
            lineNum = lineNum,
            firstRunTime = firstRunTime,
            dirUpName = dirUpName,
            lineKind = lineKind,
            lineName = lineName
        )
    }
}

fun LineModel.toEntity() = LineEntity(
    lineId = lineId ?: "",
    dirDownName = dirDownName,
    runInterval = runInterval,
    lastRunTime = lastRunTime,
    lineNum = lineNum,
    firstRunTime = firstRunTime,
    dirUpName = dirUpName,
    lineKind = lineKind,
    lineName = lineName,
    selected = false
)