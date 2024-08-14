package com.traffic.data.repository

import com.traffic.data.local.db.dao.LineDao
import com.traffic.data.local.db.entity.toLineModel
import com.traffic.domain.model.LineModel
import com.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LineRepositoryImpl @Inject constructor(
    private val lineDao: LineDao,
) : LineRepository {

    override fun getSearchedLineList(keyword: String): Flow<List<LineModel>> {
        return lineDao.getSearchedLineList(keyword).map { list ->
            list.map { it.toLineModel() }
        }
    }

    override fun getLineOne(lineId: String): LineModel {
        return lineDao.getLineOne(lineId).toLineModel()
    }

    override fun getLineKind(lineId: String): String {
        return lineDao.getLineKind(lineId)
    }
}