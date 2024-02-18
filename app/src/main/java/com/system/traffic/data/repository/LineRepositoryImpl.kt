package com.system.traffic.data.repository

import com.system.traffic.domain.dataModel.LineModel
import com.system.traffic.data.local.dataSource.FileDataSource
import com.system.traffic.data.local.db.dao.LineDao
import com.system.traffic.domain.dataModel.LineEntity
import com.system.traffic.domain.repository.LineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LineRepositoryImpl (
    private val lineDao: LineDao,
    //private val dataSource: FileDataSource,
): LineRepository {

    /*override fun getLineColor(): Flow<List<LineModel>> {
        return lineDao.getLineColor()
    }*/
}