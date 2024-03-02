package com.system.traffic.data.repository

import com.system.traffic.domain.model.LineModel
import com.system.traffic.data.local.db.dao.LineDao
import com.system.traffic.data.local.db.entity.toLineEntity
import com.system.traffic.data.local.db.entity.toLineModel
import com.system.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LineRepositoryImpl(
    private val lineDao: LineDao,
) : LineRepository {

    override fun getLineColor(): Flow<List<LineModel>> {
        return lineDao.getLineColor().map { list ->
            list.map { it.toLineModel() }
        }
    }

    override fun getLineOne(lineId: String): LineModel {
        return lineDao.getLineOne(lineId).toLineModel()
    }
}