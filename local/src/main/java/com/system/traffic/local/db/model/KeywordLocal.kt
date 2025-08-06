package com.system.traffic.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.system.traffic.local.LocalMapper
import com.system.traffic.local.RoomConstant
import com.traffic.data.model.local.KeywordEntity

@Entity(tableName = RoomConstant.Table.KEYWORD_LOCAL)
data class KeywordLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val keyword: String
): LocalMapper<KeywordEntity>{
    override fun toData(): KeywordEntity = KeywordEntity(
        id = id,
        keyword = keyword
    )
}
