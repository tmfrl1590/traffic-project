package com.traffic.presentation.screens.station.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.core.Resource
import com.traffic.domain.model.StationModel
import com.traffic.domain.usecase.keyword.DeleteKeywordUseCase
import com.traffic.domain.usecase.keyword.GetKeywordListUseCase
import com.traffic.domain.usecase.keyword.InsertKeywordUseCase
import com.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.traffic.domain.usecase.like.ToggleLikeStationUseCase
import com.traffic.domain.usecase.station.GetSearchStationUseCase
import com.traffic.presentation.screens.station.action.StationAction
import com.traffic.presentation.screens.station.state.StationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getSearchStationUseCase: GetSearchStationUseCase,
    private val getLikeStationListUseCase: GetLikeStationListUseCase,
    private val insertKeywordUseCase: InsertKeywordUseCase,
    private val getKeywordListUseCase: GetKeywordListUseCase,
    private val toggleLikeStationUseCase: ToggleLikeStationUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(value = StationState())
    val state: StateFlow<StationState> = _state.asStateFlow()

    init {
        getKeywordList()
    }

    // 키워드 리스트 조회
    private fun getKeywordList() {
        viewModelScope.launch(Dispatchers.IO) {
            getKeywordListUseCase().collectLatest { keywords ->
                _state.update { it.copy(keywordList = keywords) }
            }
        }
    }

    private var searchJob: Job? = null

    private fun getSearchedStationList(keyword: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(context = Dispatchers.IO) {
            insertKeywordUseCase(keyword = keyword)

            combine(
                getSearchStationUseCase(keyword = "%$keyword%"),
                getLikeStationListUseCase()
            ) { searchedStation, likes ->
                val likeIds = likes.map { it.arsId }.toSet()
                if (searchedStation is Resource.Success) {
                    searchedStation.data.map { it.copy(selected = it.arsId in likeIds) }
                } else {
                    emptyList()
                }
            }.collectLatest { updatedList ->
                _state.update { it.copy(searchedStationList = updatedList) }
            }
        }
    }

    // 즐겨찾기 추가/삭제
    fun toggleLikeStation(stationModel: StationModel) = viewModelScope.launch(Dispatchers.IO) {
        toggleLikeStationUseCase(stationModel)
    }

    // 키워드 입력
    fun onInputKeyword(keyword: String){
        _state.update { it.copy(keyword = keyword) }
    }

    // 키워드 1개 삭제
    fun deleteKeyword(keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            deleteKeywordUseCase(keyword = keyword)
        }
    }

    fun onAction(action: StationAction){
        when(action){
            is StationAction.OnInputKeyword -> onInputKeyword(keyword = action.keyword)
            is StationAction.OnSearchStation -> getSearchedStationList(keyword = _state.value.keyword)
            is StationAction.OnClickFavoriteIcon -> toggleLikeStation(stationModel = action.stationModel)
            is StationAction.OnClickKeyword -> getSearchedStationList(keyword = action.keyword)
            is StationAction.OnDeleteKeyword -> deleteKeyword(keyword = action.keyword)
        }
    }
}