package com.system.traffic.data

internal interface DataMapper<DomainModel> {
    fun toDomain(): DomainModel
}