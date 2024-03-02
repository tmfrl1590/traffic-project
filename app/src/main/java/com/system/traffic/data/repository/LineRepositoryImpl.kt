package com.system.traffic.data.repository

import com.system.traffic.domain.model.LineModel
import com.system.traffic.data.local.db.dao.LineDao
import com.system.traffic.data.local.db.entity.toLineEntity
import com.system.traffic.domain.repository.LineRepository

class LineRepositoryImpl (
    private val lineDao: LineDao,
    //private val dataSource: FileDataSource,
): LineRepository {

    /*override fun getLineColor(): Flow<List<LineModel>> {
        return lineDao.getLineColor()
    }*/
    override fun updateLine(lineModel: LineModel) {
        lineDao.updateLine(lineModel.toLineEntity())
    }
}