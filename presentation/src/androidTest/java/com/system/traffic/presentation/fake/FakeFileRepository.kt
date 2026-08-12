package com.system.traffic.presentation.fake

import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.repository.FileRepository

/**
 * 테스트용 FileRepository fake.
 * 생성자로 반환할 데이터를 지정할 수 있다. 기본값은 빈 리스트.
 */
class FakeFileRepository(
    private val stations: List<StationModel> = emptyList(),
    private val lines: List<LineModel> = emptyList(),
) : FileRepository {
    override fun getStationFileData(): List<StationModel> = stations
    override suspend fun insertStation(stationModel: StationModel) = Unit
    override suspend fun insertStations(stations: List<StationModel>) = Unit
    override fun getLineFileData(): List<LineModel> = lines
    override suspend fun insertLine(lineModel: LineModel) = Unit
    override suspend fun insertLines(lines: List<LineModel>) = Unit
}
