package com.system.traffic.presentation.screen.bus_arrive

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.system.traffic.common.Resource
import com.system.traffic.domain.model.BusArriveBody
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class BusArriveViewModel @Inject constructor(
    private val useCase: UseCase,
): ViewModel(){

    private val _liveData:  MutableLiveData<Resource<BusArriveBody>> = MutableLiveData()
    val liveData: MutableLiveData<Resource<BusArriveBody>> get() = _liveData

    // 버스 도착 정보 가져오기
    suspend fun getBusArriveList(arsId: String): Resource<BusArriveBody> {
        println("getBusArriveList")
        return useCase.busArriveUseCase.getBusArriveList(arsId)
        //_liveData.value = useCase.busArriveUseCase.getBusArriveList(arsId)
    }
}