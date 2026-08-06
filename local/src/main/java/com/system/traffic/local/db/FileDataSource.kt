package com.system.traffic.local.db

import android.content.Context
import com.system.traffic.data.model.local.LineDataWrapper
import com.system.traffic.data.model.local.LineEntity
import com.system.traffic.data.model.local.StationDataWrapper
import com.system.traffic.data.model.local.StationEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class FileDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
){
    private val json = Json {
        ignoreUnknownKeys = true // JSON에 정의되었으나 Entity 클래스엔 없는 필드가 있어도 예외없이 무시
        coerceInputValues = true // null 값이 들어와도 에러를 내지 않고 기본값으로 유연하게 처리
    }

    // 정류장 파일 파싱
    // 스트리밍 파싱: 파일 전체를 String으로 만들지 않아 순간 메모리 피크 감소, use로 스트림 자동 close
    @OptIn(ExperimentalSerializationApi::class)
    fun getStationDataFromFile(): List<StationEntity> {
        return context.assets.open("station.json").use { stream ->
            json.decodeFromStream<StationDataWrapper>(stream).stationList
        }
    }

    // 노선 파일 파싱
    @OptIn(ExperimentalSerializationApi::class)
    fun getLineDataFromFile(): List<LineEntity> {
        return context.assets.open("line.json").use { stream ->
            json.decodeFromStream<LineDataWrapper>(stream).lineList
        }
    }
}
