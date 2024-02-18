package com.system.traffic.presentation.screen.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.dataModel.StationModel
import com.system.traffic.domain.dataModel.StationEntity
import com.system.traffic.domain.useCase.StationUseCase
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {

    private val _stationInfo = MutableStateFlow<StationEntity>(StationEntity(0, "", "", "", "", "", "", "", false))
    val stationInfo : StateFlow<StationEntity> = _stationInfo

    private val _searchedStationList = MutableStateFlow<List<StationEntity>>(listOf())
    val searchResult : StateFlow<List<StationEntity>> = _searchedStationList



    // 즐겨찾기 - 리스트 가져오기 (즐겨찾기 페이지)
    val likeStationList = useCase.stationUseCase.getLikeStationList()

    // 정류장 검색
    fun getSearchedStationList(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = useCase.stationUseCase.getSearchedStationList(keyword = "%$keyword%")

        withContext(Dispatchers.Main){
            _searchedStationList.value = result
        }
    }

    // 정류장 즐겨찾기 추가, 삭제
    fun updateStation(stationEntity: StationEntity)= viewModelScope.launch(Dispatchers.IO) {
        useCase.stationUseCase.updateStation(stationEntity.copy(selected = !stationEntity.selected))
    }

    suspend fun getStationInfo(ars_id: String){
        useCase.stationUseCase.getStationInfo(ars_id).collectLatest {
            _stationInfo.emit(it)
        }
    }
}