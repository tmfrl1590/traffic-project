package com.system.traffic.main.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.system.traffic.dataModel.BusArriveModel
import com.system.traffic.dataModel.LineModel
import com.system.traffic.dataModel.LineStationModel
import com.system.traffic.dataModel.StationModel
import com.system.traffic.dataStore.MyDataStore
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.repository.DBRepository
import com.system.traffic.repository.NetWorkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetWorkRepository()

    private lateinit var selectedStationList: ArrayList<StationEntity>
    private lateinit var selectedLineList: ArrayList<LineEntity>
    private lateinit var busArriveList: ArrayList<BusArriveModel>
    private lateinit var lineColorList: ArrayList<LineModel>

    private lateinit var lineStationInfoList: ArrayList<LineStationModel>


    private val _resultStationInfo = MutableLiveData<StationEntity>()
    val resultStationInfo: LiveData<StationEntity>
        get() = _resultStationInfo

    private val _resultLineStationInfo = MutableLiveData<LineEntity>()
    val resultLineStationInfo: LiveData<LineEntity>
        get() = _resultLineStationInfo

    private val _resultStationList = MutableLiveData<ArrayList<StationEntity>>()
    val resultStationList: LiveData<ArrayList<StationEntity>>
        get() = _resultStationList

    private val _resultLineList = MutableLiveData<ArrayList<LineEntity>>()
    val resultLineList: LiveData<ArrayList<LineEntity>>
        get() = _resultLineList

    private val _resultBusArriveList = MutableLiveData<ArrayList<BusArriveModel>>()
    val resultBusArriveList: LiveData<ArrayList<BusArriveModel>>
        get() = _resultBusArriveList

    private val _resultBusArriveState = MutableLiveData<String>()
    val resultBusArriveState: LiveData<String>
        get() = _resultBusArriveState

    private val _resultLineColorList = MutableLiveData<ArrayList<LineModel>>()
    val resultLineColorList: LiveData<ArrayList<LineModel>>
        get() = _resultLineColorList

    private val _resultLineStationInfoList = MutableLiveData<ArrayList<LineStationModel>>()
    val resultLineStationInfoList: LiveData<ArrayList<LineStationModel>>
        get() = _resultLineStationInfoList


    // 원하는 정류장 목록 가져오기(검색)
    fun getSavedStationList(text: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = dbRepository.getSavedStationList(text)
        selectedStationList = ArrayList()
        for (i in result) {
            val a = StationEntity(
                i.id,
                i.station_num,
                i.busstop_name,
                i.next_busstop,
                i.busstop_id,
                i.ars_id,
                i.longitude,
                i.latitude,
                i.selected
            )
            selectedStationList.add(a)
        }

        withContext(Dispatchers.Main) {
            _resultStationList.value = selectedStationList
        }
    }

    fun getSavedLineList(test: String?) = viewModelScope.launch(Dispatchers.IO) {
        val result = dbRepository.getSavedLineList(test)

        selectedLineList = ArrayList()
        for (i in result) {
            val a = LineEntity(
                i.id,
                i.dir_down_name,
                i.run_interval,
                i.last_run_time,
                i.line_num,
                i.first_run_time,
                i.dir_up_name,
                i.line_id,
                i.line_kind,
                i.line_name,
                i.selected
            )
            selectedLineList.add(a)
        }

        withContext(Dispatchers.Main) {
            _resultLineList.value = selectedLineList
        }
    }

    fun getBusArrive(ars_id: String?) = viewModelScope.launch {
        val result = netWorkRepository.getBusArriveList(ars_id)

        busArriveList = ArrayList()

        if (result.row_count == "0") {

            withContext(Dispatchers.Main) {
                _resultBusArriveState.value = "0"
            }
        } else {
            for (arrive in result.itemList) {
                busArriveList.add(arrive)
            }

            withContext(Dispatchers.Main) {
                _resultBusArriveList.value = busArriveList
            }
        }

    }

    // 버스 색상 가져오기
    fun getLineColor() = viewModelScope.launch(Dispatchers.IO) {
        val result = dbRepository.getLineColor()

        lineColorList = ArrayList()

        for (lineColor in result) {
            lineColorList.add(lineColor)
        }

        withContext(Dispatchers.Main) {
            _resultLineColorList.value = lineColorList
        }
    }

    fun getLineStationInfoList(line_id: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = netWorkRepository.getLineStationInfo(line_id)

        lineStationInfoList = ArrayList()

        for (item in result.itemList) {
            lineStationInfoList.add(item)
        }

        withContext(Dispatchers.Main) {
            _resultLineStationInfoList.value = lineStationInfoList
        }
    }

    fun getLineStationInfo(line_id: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = dbRepository.getLineStationInfo(line_id)

        withContext(Dispatchers.Main) {
            _resultLineStationInfo.value = result
        }
    }

    fun getStationInfo(ars_id: String?) = viewModelScope.launch(Dispatchers.IO) {
        val result = dbRepository.getStationInfo(ars_id)

        withContext(Dispatchers.Main) {
            _resultStationInfo.value = result
        }
    }
}