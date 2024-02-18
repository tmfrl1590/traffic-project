package com.system.traffic.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.system.traffic.domain.dataModel.LineModel
import com.system.traffic.domain.dataModel.LineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LineDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLine(lineEntity: LineEntity)

    /*@Query("SELECT line_id, line_kind FROM line")
    fun getLineColor() : Flow<List<LineModel>>*/
}
