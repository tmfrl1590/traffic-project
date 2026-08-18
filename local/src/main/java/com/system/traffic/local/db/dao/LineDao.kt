package com.system.traffic.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.system.traffic.local.db.model.LineLocal

@Dao
interface LineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLine(lineLocal: LineLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLines(lines: List<LineLocal>)
}
