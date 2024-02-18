package com.system.traffic.presentation.screen.bus_arrive

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.dataModel.BusArriveBody
import com.system.traffic.domain.dataModel.BusArriveModel
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class BusArriveViewModel @Inject constructor(
    private val useCase: UseCase,
): ViewModel(){


    /*private val _resultBusArriveList = MutableLiveData<BusArriveBody>()
    val resultBusArriveList: LiveData<BusArriveBody> = _resultBusArriveList

    fun getBusArrive(ars_id: String) = viewModelScope.launch(Dispatchers.IO) {
        _resultBusArriveList.value = useCase.busArriveUseCase.getBusArriveList(ars_id)
    }*/
}