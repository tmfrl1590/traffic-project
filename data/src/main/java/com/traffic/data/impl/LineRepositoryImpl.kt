package com.traffic.data.impl

import com.traffic.domain.model.LineModel
import com.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LineRepositoryImpl @Inject constructor(
    // TODO: LineDao 구현 후 의존성 주입
) : LineRepository {

    override fun getSearchedLineList(keyword: String): Flow<List<LineModel>> {
        // TODO: 실제 DAO 구현 후 수정
        return flowOf(emptyList())
    }

    override fun getLineOne(lineId: String): LineModel {
        // TODO: 실제 DAO 구현 후 수정
        return LineModel(
            dirDownName = "",
            runInterval = "",
            lastRunTime = "",
            lineNum = "",
            firstRunTime = "",
            dirUpName = "",
            lineId = lineId,
            lineKind = "1", // 기본값
            lineName = "Unknown Line"
        )
    }

    override fun getLineKind(lineId: String): String {
        // TODO: 실제 DAO 구현 후 수정 - 임시로 노선 종류 반환
        return when {
            lineId.startsWith("1") -> "1" // 간선버스
            lineId.startsWith("2") -> "2" // 지선버스  
            lineId.startsWith("3") -> "3" // 순환버스
            lineId.startsWith("4") -> "4" // 광역버스
            lineId.startsWith("5") -> "5" // 마을버스
            lineId.startsWith("6") -> "6" // 공항버스
            else -> "1" // 기본값
        }
    }
}