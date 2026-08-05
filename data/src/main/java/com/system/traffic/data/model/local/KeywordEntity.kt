package com.system.traffic.data.model.local

import com.system.traffic.data.DataMapper
import com.system.traffic.domain.model.KeywordModel

data class KeywordEntity(
    val id: Long,
    val keyword: String
): DataMapper<KeywordModel>{
    override fun toDomain(): KeywordModel {
        return KeywordModel(
            id = id,
            keyword = keyword
        )
    }
}
