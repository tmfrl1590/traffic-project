package com.traffic.data.impl

import com.traffic.data.local.LocalDataSource
import com.traffic.domain.model.KeywordModel
import com.traffic.domain.repository.KeywordRepository
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
}