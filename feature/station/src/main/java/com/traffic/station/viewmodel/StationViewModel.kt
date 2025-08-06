package com.traffic.station.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.common.Resource
import com.traffic.domain.model.StationModel
import com.traffic.domain.usecase.keyword.InsertKeywordUseCase
import com.traffic.domain.usecase.like.AddLikeStationUseCase
import com.traffic.domain.usecase.like.DeleteLikeStationUseCase
import com.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.traffic.domain.usecase.station.GetSearchStationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getSearchStationUseCase: GetSearchStationUseCase,
    private val addLikeStationUseCase: AddLikeStationUseCase,
    private val deleteLikeStationUseCase: DeleteLikeStationUseCase,
    private val getLikeStationListUseCase: GetLikeStationListUseCase,
    private val insertKeywordUseCase: InsertKeywordUseCase,
) : ViewModel() {

    private val _likeStationList = MutableStateFlow<List<StationModel>>(listOf())
    val likeStationList: StateFlow<List<StationModel>> = _likeStationList

    // 뷰모델에서 사용할 StateFlow 정의
    private val _searchedStationList = MutableStateFlow<Resource<List<StationModel>>>(Resource.Idle())
    val searchedStationList: StateFlow<Resource<List<StationModel>>> = _searchedStationList

    private var currentKeyword: String? = null

    // 정류장 검색
    private fun getSearchedStationList(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        currentKeyword = keyword

        // 키워드 저장
        insertKeywordUseCase(keyword = keyword)

        combine(
            flow = getSearchStationUseCase(keyword = "%$keyword%"),
            flow2 = getLikeStationListUseCase()
        ) { searchedStationList, likeStationList ->
            // 좋아요 정류장의 arsId Set 생성
            val likeStationSet = likeStationList.map { it.arsId }.toSet()

            when (searchedStationList) {
                is Resource.Success -> {
                    // 좋아요 상태가 반영된 검색 결과 리스트 생성
                    val updatedList = searchedStationList.data.map { it.copy(selected = likeStationSet.contains(it.arsId)) }
                    Resource.Success(updatedList)
                }
                is Resource.Error -> { Resource.Error() }
                is Resource.Loading -> { Resource.Loading() }
                else -> Resource.Idle()
            }
        }.collectLatest { result ->
            if (currentKeyword == keyword) {
                _searchedStationList.emit(result)
            }
        }
    }

    // 즐겨찾기 추가
    fun insertLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        addLikeStationUseCase(stationModel)
    }

    // 즐겨찾기 삭제
    fun deleteLikeStation(arsId: String) = viewModelScope.launch(Dispatchers.IO) {
        deleteLikeStationUseCase(arsId)
    }

    // 즐겨찾기 리스트 조회
    fun getLikeStationList() {
        viewModelScope.launch(Dispatchers.IO) {
            getLikeStationListUseCase().collectLatest { likeStations ->
                // 즐겨찾기 리스트의 모든 항목은 selected = true로 설정
                val updatedList = likeStations.map { it.copy(selected = true) }
                _likeStationList.emit(updatedList)
            }
        }
    }

    fun onStationUIEvents(stationUIEvents: StationUIEvents){
        when(stationUIEvents){
            StationUIEvents.OnStationCardClick -> {}
            is StationUIEvents.OnSearchStationList -> getSearchedStationList(stationUIEvents.keyword)
            is StationUIEvents.OnFavoriteIconClick -> {
                if(stationUIEvents.stationModel.selected){
                    deleteLikeStation(stationUIEvents.stationModel.busStopId ?: "")
                }else {
                    insertLikeStation(stationUIEvents.stationModel)
                }
            }
        }
    }
}