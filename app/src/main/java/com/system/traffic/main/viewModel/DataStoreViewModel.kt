package com.system.traffic.main.viewModel

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.traffic.App
import com.system.traffic.dataStore.MyDataStore
import com.system.traffic.main.other.SuggestActivity
import kotlinx.coroutines.launch

class DataStoreViewModel : ViewModel()  {

    private val _resultArriveColor = MutableLiveData<String>()
    val resultArriveColor: LiveData<String>
        get() = _resultArriveColor

    private val _resultAlarmServiceStation = MutableLiveData<String>()
    val resultAlarmServiceStation : LiveData<String>
        get() = _resultAlarmServiceStation

    private val _resultAlarmServiceLine = MutableLiveData<String>()
    val resultAlarmServiceLine : LiveData<String>
        get() = _resultAlarmServiceLine

    private val _resultAlarmReloadTime = MutableLiveData<String>()
    val resultAlarmReloadTime : LiveData<String>
        get() = _resultAlarmReloadTime

    // 설정 - 버스도착시간 색상 저장
    fun setArriveColor(color: String) = viewModelScope.launch {
        MyDataStore().setArriveColor(color)
    }

    // 설정 - 버스도착시간 색상값 가져오기
    fun getArriveColor() = viewModelScope.launch {
        val result = MyDataStore().getArriveColor()
        _resultArriveColor.value = result
    }

    // 현재 실행중인 알람 서비스 값 저장 - 정류장
    fun setAlarmServiceStation(busStopId : String) = viewModelScope.launch {
        MyDataStore().setAlarmServiceStation(busStopId)
    }

    // 현재 실행중인 알람 서비스 값 저장 - 버스
    fun setAlarmServiceLine(lineName: String) = viewModelScope.launch {
        MyDataStore().setAlarmServiceLine(lineName)
    }

    // 현재 실행중인 알람 서비스 값 가져오기 - 정류장
    fun getAlarmServiceStation() = viewModelScope.launch {
        var result = MyDataStore().getAlarmServiceStation()
        _resultAlarmServiceStation.value = result
    }

    // 현재 실행중인 알람 서비스 값 가져오기 - 버스
    fun getAlarmServiceLine() = viewModelScope.launch {
        var result = MyDataStore().getAlarmServiceLine()
        _resultAlarmServiceLine.value = result
    }

    // 알람 갱신시간 세팅
    fun setAlarmReloadTime(reloadTime : String) = viewModelScope.launch {
        MyDataStore().setAlarmReloadTime(reloadTime)
    }

    // 알람 갱신시간 값 가져오기
    fun getAlarmReloadTime() = viewModelScope.launch {
        var result = MyDataStore().getAlarmReloadTime()
        _resultAlarmReloadTime.value = result
    }
}