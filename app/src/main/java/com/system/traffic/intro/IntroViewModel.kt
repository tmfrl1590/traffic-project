package com.system.traffic.intro

import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.system.traffic.dataStore.MyDataStore
import com.system.traffic.dataModel.LineModel
import com.system.traffic.dataModel.StationModel
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.repository.DBRepository
import kotlinx.coroutines.*
import org.json.JSONObject

class IntroViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    private val _isFirstLogin = MutableLiveData<Boolean>()
    val isFirstLogin : LiveData<Boolean>
        get() = _isFirstLogin

    fun setUpIsFirstLogin() = viewModelScope.launch {
        MyDataStore().setUpIsFirstLogin()
    }

    fun checkFlag() = viewModelScope.launch {
        delay(3000)

        val getData = MyDataStore().checkIsFirstLogin()
        _isFirstLogin.value = getData
    }

    fun insertStationInfo(stationList : ArrayList<StationModel>){

        for(i in stationList){
            val stationEntity = StationEntity(
                0,
                i.station_num,
                i.busstop_name,
                i.next_busstop,
                i.busstop_id,
                i.ars_id,
                i.longitude,
                i.latitude,
                false
            )

            stationEntity.let {
                dbRepository.insertStation(it)
            }
        }
    }

    fun insertLineInfo(lineList : ArrayList<LineModel>){
        for (i in lineList) {
            val lineEntity = LineEntity(
                0,
                i.dir_down_name,
                i.run_interval,
                i.last_run_time,
                i.line_num,
                i.first_run_time,
                i.dir_up_name,
                i.line_id,
                i.line_kind,
                i.line_name,
                false
            )

            lineEntity.let {
                dbRepository.insertLine(lineEntity)
            }
        }
    }
}