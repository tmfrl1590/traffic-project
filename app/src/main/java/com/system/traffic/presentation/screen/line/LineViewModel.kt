package com.system.traffic.presentation.screen.line

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.useCase.line.GetLineColorUseCase
import com.system.traffic.domain.useCase.line.GetSearchLineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineViewModel @Inject constructor(
    private val getLineColorUseCase: GetLineColorUseCase,
    private val getSearchLineUseCase: GetSearchLineUseCase,
): ViewModel(){

    private val _lineColorList = MutableStateFlow<List<LineModel>>(listOf())
    val lineColorList : StateFlow<List<LineModel>> = _lineColorList

    private val _searchedLineList = MutableStateFlow<List<LineModel>>(listOf())
    val searchedLineList : StateFlow<List<LineModel>> = _searchedLineList

    // 노선 검색
    fun getSearchedLineList(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        getSearchLineUseCase(keyword = "%$keyword%").collectLatest {
            _searchedLineList.emit(it)
        }
    }

    // 버스 색상 가져오기(종류 가져오기)
    fun getLineColor() {
        viewModelScope.launch(Dispatchers.IO) {
            getLineColorUseCase().collectLatest {
                _lineColorList.emit(it)
            }
        }
    }
}