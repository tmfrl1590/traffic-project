package com.traffic.domain.repository

import com.traffic.domain.model.KeywordModel
import kotlinx.coroutines.flow.Flow

interface KeywordRepository {

    // 키워드 저장
    suspend fun insertKeyword(keyword: String)

    // 키워드 가져오기
    fun getKeywordList(): Flow<List<KeywordModel>>

    // 키워드 삭제하기
    suspend fun deleteKeyword(keyword: String)
}