package com.traffic.data

internal interface DataMapper<DomainModel> {
    fun toDomain(): DomainModel
}