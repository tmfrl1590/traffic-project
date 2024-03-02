package com.system.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import com.system.traffic.data.local.db.entity.LineEntity

@Dao
interface LineDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLine(lineEntity: LineEntity)

    /*@Query("SELECT line_id, line_kind FROM line")
    fun getLineColor() : Flow<List<LineModel>>*/

    // 노선 즐겨찾기 추가, 삭제
    @Update
    fun updateLine(lineEntity: LineEntity)
}
