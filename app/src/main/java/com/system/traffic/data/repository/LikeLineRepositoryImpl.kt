package com.system.traffic.data.repository

import com.system.traffic.data.local.db.dao.LikeLineDao
import com.system.traffic.data.local.db.entity.toLikeLineEntity
import com.system.traffic.data.local.db.entity.toLikeLineModel
import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.repository.LikeLineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LikeLineRepositoryImpl(
    private val likeLineDao: LikeLineDao,
): LikeLineRepository {
    override suspend fun addLikeLine(lineModel: LineModel) {
        likeLineDao.addLikeLine(lineModel.toLikeLineEntity())
    }

    override suspend fun deleteLikeLine(lineId: String) {
        likeLineDao.deleteLikeLine(lineId)
    }

    override fun getLikeLineList(): Flow<List<LineModel>> {
        return likeLineDao.getLikeLineList().map { list ->
            list.map { it.toLikeLineModel() }
        }
    }
}