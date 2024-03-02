package com.system.traffic.presentation.screen.line

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel(){

    private val _lineColorList = MutableStateFlow<List<LineModel>>(listOf())
    val lineColorList : StateFlow<List<LineModel>> = _lineColorList

    private val _likeLineList = MutableStateFlow<List<LineModel>>(listOf())
    val likeLineList : StateFlow<List<LineModel>> = _likeLineList

    private val _lineInfo = MutableStateFlow(LineModel("", "", "", "", "", "" ,"", "", ""))
    val lineInfo : StateFlow<LineModel> = _lineInfo

    // 버스 색상 가져오기(종류 가져오기)
    fun getLineColor() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.lineUseCase.getLineColor().collectLatest {
                _lineColorList.emit(it)
            }
        }
    }

    // 노선 한 건 조회
    fun getLineOne(lineId: String) = viewModelScope.launch(Dispatchers.IO) {
        useCase.lineUseCase.getLineOne(lineId).let {
            _lineInfo.emit(it)
        }
    }

    // 즐겨찾기 추가
    fun insertLikeLine(lineId: String)= viewModelScope.launch(Dispatchers.IO) {
        val lineModel = useCase.lineUseCase.getLineOne(lineId)
        useCase.likeLineUseCase.addLikeLine(lineModel)
    }

    // 즐겨찾기 삭제
    fun deleteLikeLine(lineId: String)= viewModelScope.launch(Dispatchers.IO) {
        useCase.likeLineUseCase.deleteLikeLine(lineId)
    }

    // 즐겨찾기 리스트 조회
    fun getLikeLineList1(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.likeLineUseCase.getLikeLineList().collectLatest {
                _likeLineList.emit(it)
            }
        }
    }
}