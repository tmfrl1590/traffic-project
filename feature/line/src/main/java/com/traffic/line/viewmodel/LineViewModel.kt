package com.traffic.line.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffic.domain.model.LineModel
import com.traffic.domain.useCase.line.GetSearchLineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineViewModel @Inject constructor(
    private val getSearchLineUseCase: GetSearchLineUseCase,
): ViewModel(){

    private val _searchedLineList = MutableStateFlow<List<LineModel>>(listOf())
    val searchedLineList : StateFlow<List<LineModel>> = _searchedLineList

    // 노선 검색
    fun getSearchedLineList(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        getSearchLineUseCase(keyword = "%$keyword%").collectLatest {
            _searchedLineList.emit(it)
        }
    }
}