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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeywordRow(keywordLocal: KeywordLocal)

    /** 같은 검색어는 한 줄만 두고, 다시 검색하면 최신 순으로 갱신합니다. */
    @Transaction
    suspend fun insertKeyword(keywordLocal: KeywordLocal) {
        deleteKeyword(keywordLocal.keyword)
        insertKeywordRow(keywordLocal)
    }

    // 키워드당 가장 최근 행만 남긴 뒤, 최근 입력 순으로 최대 5개
    @Query(
        """
        SELECT * FROM keyword_entity
        WHERE id IN (SELECT MAX(id) FROM keyword_entity GROUP BY keyword)
        ORDER BY id DESC
        LIMIT 5
        """,
    )
    fun getKeywordList(): Flow<List<KeywordLocal>>

    // 키워드 1개 삭제하기
    @Query(
        """
            DELETE FROM keyword_entity WHERE keyword = :keyword
        """
    )
    suspend fun deleteKeyword(keyword: String)

    // 전체 키워드 삭제하기
    @Query(
        """
            DELETE FROM keyword_entity
            """
    )
    suspend fun allDeleteKeyword()
}