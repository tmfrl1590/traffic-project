package com.system.traffic.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.system.traffic.local.db.model.KeywordLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface KeywordDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyword(keywordLocal: KeywordLocal)

    // 키워드 리스트 조회 (최근 입력 순)
    @Query("SELECT * FROM keyword_entity ORDER BY id DESC LIMIT 5")
    fun getKeywordList(): Flow<List<KeywordLocal>>
}