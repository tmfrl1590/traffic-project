package com.system.traffic.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.system.traffic.dataModel.LineModel
import com.system.traffic.dataModel.StationModel
import com.system.traffic.databinding.ActivityIntroBinding
import com.system.traffic.main.MainActivity
import com.system.traffic.main.viewModel.DataStoreViewModel
import com.system.traffic.util.SettingUtil
import kotlinx.coroutines.*
import org.json.JSONObject

class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding

    private val introViewModel : IntroViewModel by viewModels()
    private val dataStoreViewModel : DataStoreViewModel by viewModels()

    private lateinit var stationList : ArrayList<StationModel>
    private lateinit var lineList : ArrayList<LineModel>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        introViewModel.checkFlag()
        introViewModel.isFirstLogin.observe(this) {

            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                runBlocking {
                    setStationLineData()
                }
            }
        }
    }

    private suspend fun setStationLineData(){

        val job = lifecycleScope.launch(Dispatchers.IO){
            val stationJob = launch { getStationDataFromFile() }
            val lineJob = launch { getLineDataFromFile() }

            stationJob.join()
            lineJob.join()

            launch {
                introViewModel.insertStationInfo(stationList)
                introViewModel.insertLineInfo(lineList)
            }
        }

        job.join()

        introViewModel.setUpIsFirstLogin()
        dataStoreViewModel.setArriveColor(SettingUtil.RED)
        SettingUtil.BUS_ARRIVE_COLOR = SettingUtil.RED

        //dataStoreViewModel.setAlarmReloadTime("30000")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getStationDataFromFile() {
        stationList = ArrayList()

        val jsonString = assets.open("station.json").reader().readText()
        val resultList = JSONObject(jsonString).getJSONArray("STATION_LIST")

        for(i in 0 until resultList.length()){
            val jsonObject = resultList.getJSONObject(i)
            val stationNum = jsonObject.getString("STATION_NUM").toString()
            val busStopName = jsonObject.getString("BUSSTOP_NAME").toString()
            val arsId = jsonObject.getString("ARS_ID").toString()
            val nextBusStop = jsonObject.getString("NEXT_BUSSTOP").toString()
            val busStopId = jsonObject.getString("BUSSTOP_ID").toString()
            val longitude = jsonObject.getString("LONGITUDE").toString()
            val latitude = jsonObject.getString("LATITUDE").toString()

            var station = StationModel(stationNum, busStopName, nextBusStop, busStopId, arsId, longitude, latitude, "0")
            stationList.add(station)
        }
    }

    private fun getLineDataFromFile(){
        lineList = ArrayList()

        val jsonString = assets.open("line.json").reader().readText()
        val resultList = JSONObject(jsonString).getJSONArray("LINE_LIST")

        for(i in 0 until resultList.length()){
            val jsonObject = resultList.getJSONObject(i)
            val dirDownName = jsonObject.getString("DIR_DOWN_NAME").toString()
            val runInterval = jsonObject.getString("RUN_INTERVAL").toString()
            val lastRunTime = jsonObject.getString("LAST_RUN_TIME").toString()
            val lineNum = jsonObject.getString("LINE_NUM").toString()
            val firstRunTime = jsonObject.getString("FIRST_RUN_TIME").toString()
            val dirUpName = jsonObject.getString("DIR_UP_NAME").toString()
            val lineId = jsonObject.getString("LINE_ID").toString()
            val lineKind = jsonObject.getString("LINE_KIND").toString()
            val lineName = jsonObject.getString("LINE_NAME").toString()

            val line = LineModel(dirDownName, runInterval, lastRunTime, lineNum, firstRunTime, dirUpName, lineId, lineKind, lineName, "0")
            lineList.add(line)
        }
    }
}