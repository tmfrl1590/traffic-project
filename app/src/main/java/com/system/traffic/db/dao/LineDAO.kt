package com.system.traffic.db.dao

import androidx.room.*
import com.system.traffic.dataModel.LineModel
import com.system.traffic.db.entity.LineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLine(lineEntity: LineEntity)

    // 버스 검색
    @Query("SELECT * FROM line WHERE line_name LIKE :text ")
    fun getSearchedLineList(text : String?) : List<LineEntity>


    @Query("SELECT line_id, line_kind FROM line  ")
    fun getLineColor() : List<LineModel>


    // 버스 - 즐겨찾기 목록 가져오기
    @Query("SELECT * FROM line WHERE selected ==:isLike")
    fun getLikeLineList(isLike: Boolean) : Flow<List<LineEntity>>


    // 즐겨찾기 - 버스 추가, 삭제
    @Update
    fun updateLine(lineEntity: LineEntity)

    // 즐겨찾기 - 버스 추가 삭제(상세화면에서)
    @Query("UPDATE line SET selected =:text WHERE line_id =:lineId")
    fun updateLineLike(text : String, lineId: String)

    // 즐겨찾기 - 해당 버스가 이미 즐겨찾기 되있는지 확인여부
    @Query("SELECT * FROM line WHERE line_id =:lineId and selected = '1'")
    fun getIsLikeLine(lineId : String) : LineEntity

    // 버스 정보 가져오기 1대
    @Query("SELECT * FROM line WHERE line_id =:lineId")
    fun getLineStationInfo(lineId: String) : LineEntity
}