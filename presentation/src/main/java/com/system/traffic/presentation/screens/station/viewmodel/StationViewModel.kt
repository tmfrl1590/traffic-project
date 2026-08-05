package com.system.traffic.presentation.screens.station.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.model.StationModel
import com.system.traffic.domain.usecase.keyword.ClearAllKeywordUseCase
import com.system.traffic.domain.usecase.keyword.DeleteKeywordUseCase
import com.system.traffic.domain.usecase.keyword.GetKeywordListUseCase
import com.system.traffic.domain.usecase.keyword.InsertKeywordUseCase
import com.system.traffic.domain.usecase.like.GetLikeStationListUseCase
import com.system.traffic.domain.usecase.like.ToggleLikeStationUseCase
import com.system.traffic.domain.usecase.station.GetSearchStationUseCase
import com.system.traffic.presentation.event.UiEvent
import com.system.traffic.presentation.event.UiEventBus
import com.system.traffic.presentation.screens.station.action.StationAction
import com.system.traffic.presentation.screens.station.state.StationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
    private val clearAllKeywordUseCase: ClearAllKeywordUseCase,
    private val uiEventBus: UiEventBus,
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
                getSearchStationUseCase(keyword = keyword),
                getLikeStationListUseCase()
            ){ searchedStation, likes ->
                val likeIds = likes.map { it.arsId }.toSet()
                searchedStation.map { it.copy(selected = it.arsId in likeIds) }
            }
                .catch { uiEventBus.sendEvent(UiEvent.ShowSnackBar(message = "오류가 발생하였습니다.")) } // repository에서 흘려보낸 예외 처리
                .collectLatest { updatedList ->
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

    // 전체 키워드 삭제하기
    fun clearAllKeywords(){
        viewModelScope.launch {
            clearAllKeywordUseCase()
        }
    }

    // 입력된 텍스트 전체 삭제
    fun clearInputText(){
        _state.update { it.copy(keyword = "") }
    }

    fun onAction(action: StationAction){
        when(action){
            is StationAction.OnInputKeyword -> onInputKeyword(keyword = action.keyword)
            is StationAction.OnSearchStation -> getSearchedStationList(keyword = _state.value.keyword)
            is StationAction.OnClickFavoriteIcon -> toggleLikeStation(stationModel = action.stationModel)
            is StationAction.OnClickKeyword -> getSearchedStationList(keyword = action.keyword)
            is StationAction.OnDeleteKeyword -> deleteKeyword(keyword = action.keyword)
            StationAction.OnClearAllKeywordList -> clearAllKeywords()
            StationAction.OnClearInputText -> clearInputText()
        }
    }
}