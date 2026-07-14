package com.system.traffic.local.db

import android.content.Context
import com.traffic.data.model.local.LineDataWrapper
import com.traffic.data.model.local.LineEntity
import com.traffic.data.model.local.StationDataWrapper
import com.traffic.data.model.local.StationEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FileDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
){
    private val json = Json {
        ignoreUnknownKeys = true // JSON에 정의되었으나 Entity 클래스엔 없는 필드가 있어도 예외없이 무시
        coerceInputValues = true // null 값이 들어와도 에러를 내지 않고 기본값으로 유연하게 처리
    }

    // 정류장 파일 파싱
    fun getStationDataFromFile(): List<StationEntity> {
        val jsonString = context.assets.open("station.json").reader().readText()
        // 단 한 줄로 파싱 완료 (자동으로 List<StationEntity>로 역직렬화됨)
        val wrapper = json.decodeFromString<StationDataWrapper>(jsonString)
        return wrapper.stationList // 지역 변수만 반환하므로 함수가 끝난 후 GC에 의해 자동 소멸
    }

    // 노선 파일 파싱
    fun getLineDataFromFile(): List<LineEntity> {
        val jsonString = context.assets.open("line.json").reader().readText()
        // 단 한 줄로 파싱 완료
        val wrapper = json.decodeFromString<LineDataWrapper>(jsonString)
        return wrapper.lineList
    }
}