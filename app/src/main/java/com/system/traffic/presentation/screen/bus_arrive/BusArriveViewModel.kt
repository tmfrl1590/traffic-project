package com.system.traffic.presentation.screen.bus_arrive

import androidx.lifecycle.ViewModel
import com.system.traffic.common.Resource
import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BusArriveViewModel @Inject constructor(
    private val useCase: UseCase,
): ViewModel(){

    // 버스 도착 정보 가져오기
    suspend fun getBusArriveList(arsId: String): Resource<BusArriveBody> {
        return useCase.busArriveUseCase.getBusArriveList(arsId)
    }
}