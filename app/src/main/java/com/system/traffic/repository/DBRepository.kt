package com.system.traffic.repository

import com.system.traffic.App
import com.system.traffic.dataModel.StationModel
import com.system.traffic.db.TrafficDataBase
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import kotlinx.coroutines.flow.flow


class DBRepository {
    val context = App.context()
    val db = TrafficDataBase.getDatabase(context)

    // 검색
    fun getSearchedStationList(text : String) = db.stationDAO().getSearchedStationList(text)
    fun getSearchedLineList(text: String?) = db.lineDAO().getSearchedLineList(text)

    // 파일데이터 DB 저장
    fun insertStation(stationEntity: StationEntity) = db.stationDAO().insertStation(stationEntity)
    fun insertLine(lineEntity: LineEntity) = db.lineDAO().insertLine(lineEntity)



    fun getLineColor() = db.lineDAO().getLineColor()


    // 즐겨찾기 가져오기(정류장)
    fun getLikeStationList() = db.stationDAO().getLikeStationList()

    // 즐겨찾기 가져오기(버스)
    fun getLikeLineList() = db.lineDAO().getLikeLineList()

    // 즐겨찾기 추가, 삭제 - 정류장
    fun updateStation(stationEntity: StationEntity) = db.stationDAO().updateStation(stationEntity)

    // 즐겨찾기 추가, 삭제 - 버스
    fun updateLine(lineEntity: LineEntity) = db.lineDAO().updateLine(lineEntity)

    // 즐겨찾기 - 정류장 추가 삭제(상세화면에서)
    fun updateStationLike(text: String, ars_id: String) = db.stationDAO().updateStationLike(text, ars_id)

    // 즐겨찾기 - 버스 추가 삭제(상세화면에서)
    fun updateLineLike(text: String, lineId: String) = db.lineDAO().updateLineLike(text, lineId)

    // 즐겨찾기 - 해당 버스가 이미 즐겨찾기 되있는지 확인여부
    fun getIsLikeLine(lineId: String) = db.lineDAO().getIsLikeLine(lineId)

    // 즐겨찾기 - 해당 정류소가 이미 즐겨찾기 되있는지 확인여부
    fun getIsLikeStation(ars_id: String) = db.stationDAO().getIsLikeStation(ars_id)

    // 해당 버스 정보 가져오기 (1대)
    fun getLineStationInfo(line_id : String) = db.lineDAO().getLineStationInfo(line_id)

    // 정류장1개 정보 가져오기
    fun getStationInfo(ars_id : String?) = db.stationDAO().getStationInfo(ars_id)
}