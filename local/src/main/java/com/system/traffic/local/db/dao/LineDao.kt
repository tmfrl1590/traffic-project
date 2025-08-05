package com.system.traffic.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.system.traffic.local.db.model.LineLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLine(lineLocal: LineLocal)

    /*@Query("SELECT * FROM line_local WHERE line_name LIKE :text")
    fun getSearchedLineList(text: String): Flow<List<LineLocal>>

    // 노선 한건 조회
    @Query("SELECT * FROM line_local WHERE line_id=:lineId")
    fun getLineOne(lineId: String): LineLocal

    // 노선 LineKind 조회
    @Query("SELECT line_kind FROM line_local WHERE line_id=:lineId")
    fun getLineKind(lineId: String): String*/
}
