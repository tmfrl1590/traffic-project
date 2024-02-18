package com.system.traffic.presentation.screen.line

import androidx.lifecycle.ViewModel
import com.system.traffic.domain.dataModel.LineModel
import com.system.traffic.domain.dataModel.LineEntity
import com.system.traffic.domain.useCase.LineUseCase
import com.system.traffic.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
}