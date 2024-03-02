package com.system.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.system.traffic.data.local.db.entity.LineEntity
import com.system.traffic.domain.model.LineModel
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLine(lineEntity: LineEntity)

    @Query("SELECT * FROM line_entity")
    fun getLineColor() : Flow<List<LineEntity>>

    // 노선 한건 조회
    @Query("SELECT * FROM line_entity WHERE line_id=:lineId")
    fun getLineOne(lineId: String): LineEntity
}
