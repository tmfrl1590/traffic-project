package com.system.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.system.traffic.data.local.db.entity.LineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLine(lineEntity: LineEntity)

    @Query("SELECT * FROM line_entity")
    fun getLineColor() : Flow<List<LineEntity>>

    @Query("SELECT * FROM line_entity WHERE line_name LIKE :text")
    fun getSearchedLineList(text: String): Flow<List<LineEntity>>

    // 노선 한건 조회
    @Query("SELECT * FROM line_entity WHERE line_id=:lineId")
    fun getLineOne(lineId: String): LineEntity
}
