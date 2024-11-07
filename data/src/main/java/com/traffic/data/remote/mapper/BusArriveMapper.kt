package com.traffic.data.remote.mapper

import com.traffic.data.remote.dto.BusArriveItemDto
import com.traffic.domain.model.BusArriveItem

object BusArriveMapper {
    fun mapperToBusArriveItem(itemList : List<BusArriveItemDto>): List<BusArriveItem> {
        return itemList.map { busArriveItemDto ->
            mapperToBusArriveItem(busArriveItemDto)
        }
    }

    fun mapperToBusArriveItem(busArriveItemDto: BusArriveItemDto): BusArriveItem {
        return BusArriveItem(
            arrive = busArriveItemDto.arrive,
            remainStop = busArriveItemDto.remainStop,
            shortLineName = busArriveItemDto.shortLineName,
            busId = busArriveItemDto.busId,
            metroFlag = busArriveItemDto.metroFlag,
            busStopName = busArriveItemDto.busStopName,
            currStopId = busArriveItemDto.currStopId,
            lineId = busArriveItemDto.lineId,
            remainMin = busArriveItemDto.remainMin,
            engBusStopName = busArriveItemDto.engBusStopName,
            dirStart = busArriveItemDto.dirStart,
            dir = busArriveItemDto.dir,
            dirEnd = busArriveItemDto.dirEnd,
            lowBus = busArriveItemDto.lowBus,
            arriveFlag = busArriveItemDto.arriveFlag,
            lineName = busArriveItemDto.lineName,
            lineKind = busArriveItemDto.lineKind,
        )
    }
}