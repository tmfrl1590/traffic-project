package com.system.traffic.presentation.screen.line

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.domain.model.LineModel
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LineViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel(){

    private val _lineColorList = MutableStateFlow<List<LineModel>>(listOf())
    val lineColorList : StateFlow<List<LineModel>> = _lineColorList




    // 버스 색상 가져오기(종류 가져오기)
    /*suspend fun getLineColor() {
        lineUseCase.getLineColor().collectLatest {
            _lineColorList.emit(it)
        }
    }*/

    // 노선 즐겨찾기 추가, 삭제
    fun updateLine(lineModel: LineModel)= viewModelScope.launch(Dispatchers.IO) {
        useCase.lineUseCase.updateLine(lineModel.copy(selected = !lineModel.selected))
    }
}