package com.system.traffic.data.impl

import com.system.traffic.data.local.LocalDataSource
import com.system.traffic.domain.model.KeywordModel
import com.system.traffic.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): KeywordRepository {
    override suspend fun insertKeyword(keyword: String) {
        localDataSource.insertKeyword(keyword = keyword)
    }

    override fun getKeywordList(): Flow<List<KeywordModel>> {
        return localDataSource.getKeywordList().map { keywordList ->
                keywordList.map { keywordEntity ->
                    keywordEntity.toDomain()
                }
            }
    }

    override suspend fun deleteKeyword(keyword: String) {
        localDataSource.deleteKeyword(keyword = keyword)
    }

    override suspend fun clearAllKeyword() {
        localDataSource.clearAllKeyword()
    }
}