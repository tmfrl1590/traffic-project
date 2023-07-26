package com.system.traffic.main

import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.system.traffic.R
import com.system.traffic.dataModel.BusArriveModel
import com.system.traffic.dataModel.LineModel
import com.system.traffic.databinding.ActivityBusArriveBinding
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.main.adapter.BusArriveAdapter
import com.system.traffic.main.viewModel.DataStoreViewModel
import com.system.traffic.main.viewModel.LikeViewModel
import com.system.traffic.main.viewModel.MainViewModel
import com.system.traffic.service.AlarmService

class BusArriveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusArriveBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val likeViewModel: LikeViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    private lateinit var busArriveAdapter: BusArriveAdapter

    private lateinit var busArriveList: ArrayList<BusArriveModel>
    private lateinit var busColorList: ArrayList<LineModel>
    private lateinit var likeLineList: ArrayList<LineEntity>

    private var busStopId: String = ""

    private var selected: String = "0"

    private var busStopName: String = ""
    private var nextBusStop: String = ""

    private var remainStop: String = ""
    private var lineName: String = ""

    private var arsId: String = ""

    private var reloadTime: String = ""

    private lateinit var color: String

    var serviceMap = mutableMapOf("alarmStation" to "", "alarmLine" to "")
    var serviceStation: String = ""
    var serviceLine: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityBusArriveBinding?>(this, R.layout.activity_bus_arrive)
        binding.activity = this@BusArriveActivity

        //setContentView(binding.root)

        dataStoreViewModel.getArriveColor()
        dataStoreViewModel.resultArriveColor.observe(this) {
            color = it
        }

        /*dataStoreViewModel.getAlarmReloadTime()
        dataStoreViewModel.resultAlarmReloadTime.observe(this) {
            reloadTime = it
        }*/

        initView()
        initEvent()
    }

    fun reloadBusArriveInfo(){
        val currDegree = binding.btnReload.rotationX
        ObjectAnimator.ofFloat(binding.btnReload, View.ROTATION, currDegree, currDegree + 180f)
            .setDuration(300).start()

        setRV()
        /*binding.btnReload.setOnClickListener {
            val currDegree = binding.btnReload.rotationX
            ObjectAnimator.ofFloat(binding.btnReload, View.ROTATION, currDegree, currDegree + 180f)
                .setDuration(300).start()

            setRV()
        }*/
    }

    private fun initEvent() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = false
            setRV()
        }
    }

    private fun setRV() {

        busArriveList = ArrayList()
        busColorList = ArrayList()
        likeLineList = ArrayList()

        mainViewModel.getBusArrive(busStopId)
        // 해당 정류소의 버스정보가 있으면 - 리사이클러 세팅
        mainViewModel.resultBusArriveList.observe(this) {

            busArriveList = it

            mainViewModel.getLineColor()
            mainViewModel.resultLineColorList.observe(this) {

                busColorList = it

                /*likeViewModel.getLikeLineList()
                likeViewModel.likeLineList.observe(this) {

                    for (item in it) {
                        likeLineList.add(item)
                    }

                    busArriveAdapter = BusArriveAdapter(
                        this,
                        busArriveList,
                        busColorList,
                        likeLineList,
                        color,
                        busStopId,
                        serviceMap
                    )
                    binding.rvBusArriveList.adapter = busArriveAdapter
                    binding.rvBusArriveList.layoutManager = LinearLayoutManager(this)

                    binding.rvBusArriveList.visibility = View.VISIBLE
                    binding.noBusInfo.visibility = View.INVISIBLE

                    busArriveAdapter.itemClick = object : BusArriveAdapter.ItemClick {
                        override fun onClick(view: View, position: Int) {
                            likeViewModel.updateLineLike(busArriveList[position].line_id!!)
                            likeLineList.clear()
                            busArriveAdapter.notifyDataSetChanged()
                        }
                    }

                    busArriveAdapter.itemClick2 = object : BusArriveAdapter.ItemClick2 {
                        override fun onClick(view: View, position: Int) {

                            lineName = busArriveList[position].line_name!!
                            remainStop = busArriveList[position].remain_stop!!

                            if (isMyServiceRunning(AlarmService::class.java)) { // 서비스가 실행중이면 - 확인창

                                if (serviceStation == busStopId && serviceLine == lineName) { // 이미 실행중인거 또 누를 때 - 실행취소
                                    dataStoreViewModel.setAlarmServiceStation("")
                                    dataStoreViewModel.setAlarmServiceLine("")

                                    //stopAlarmService()

                                } else { //
                                    alertDialog()

                                }
                            } else { // 서비스가 실행중이지 않으면 - 바로 실행

                                dataStoreViewModel.setAlarmServiceStation(busStopId)
                                dataStoreViewModel.setAlarmServiceLine(lineName)

                                serviceMap["alarmStation"] = busStopId
                                serviceMap["alarmLine"] = lineName

                                serviceStation = busStopId
                                serviceLine = lineName

                                //startAlarmService()

                                busArriveAdapter.notifyDataSetChanged()
                            }

                        }
                    }
                }*/
            }
        }

        // 해당 정류소의 버스정보가 없으면
        mainViewModel.resultBusArriveState.observe(this) {
            if (it == "0") {
                binding.rvBusArriveList.visibility = View.INVISIBLE
                binding.noBusInfo.visibility = View.VISIBLE
            }
        }
    }

    /*private fun startAlarmService() {

        val intent = Intent(this, AlarmService::class.java).apply {
            action = ACTION_START
            putExtra("busStopName", busStopName) // 현재 정류장 이름
            putExtra("busstop_id", busStopId)
            putExtra("line_name", lineName)
            putExtra("remain_stop", remainStop)
            putExtra("reloadTime", reloadTime)
            putExtra("arsId", arsId)
        }
        startService(intent)
    }*/

    /*private fun stopAlarmService() {
        val intent = Intent(this, AlarmService::class.java).apply {
            action = ACTION_STOP
        }
        startService(intent)

        serviceMap["alarmStation"] = busStopId
        serviceMap["alarmLine"] = ""

        busArriveAdapter.notifyDataSetChanged()
    }*/

    private fun alertDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()

        val okButton = mDialogView.findViewById<Button>(R.id.successButton)
        okButton.setOnClickListener {

            dataStoreViewModel.setAlarmServiceStation(busStopId)
            dataStoreViewModel.setAlarmServiceLine(lineName)

            serviceMap["alarmStation"] = busStopId
            serviceMap["alarmLine"] = lineName

            serviceStation = busStopId
            serviceLine = lineName

            //startAlarmService()

            busArriveAdapter.notifyDataSetChanged()

            mAlertDialog.dismiss()
        }

        val noButton = mDialogView.findViewById<Button>(R.id.closeButton)
        noButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    /*private fun getAlarmService() {
        dataStoreViewModel.getAlarmServiceStation()
        dataStoreViewModel.resultAlarmServiceStation.observe(this) {
            serviceStation = it
            serviceMap["alarmStation"] = serviceStation

            dataStoreViewModel.getAlarmServiceLine()
            dataStoreViewModel.resultAlarmServiceLine.observe(this) {
                serviceLine = it
                serviceMap["alarmLine"] = serviceLine
            }
        }
    }*/

    private fun initView() {
        arsId = intent.getStringExtra("ars_id").toString()

        mainViewModel.getStationInfo(arsId)
        mainViewModel.resultStationInfo.observe(this) {
            busStopName = it.busstop_name!!
            nextBusStop = it.next_busstop!!
            selected = it.selected!!
            busStopId = it.busstop_id!!

            binding.stationName.text = "$busStopName($arsId)"
            binding.nextBusStop.text = nextBusStop + "방면 "

            setRV()
        }


        /*if (!isMyServiceRunning(AlarmService::class.java)) {
            dataStoreViewModel.setAlarmServiceStation("")
            dataStoreViewModel.setAlarmServiceLine("")
        }*/

        //getAlarmService()


        // 즐겨찾기 - 세팅
        getLikeInfo(arsId)
        binding.btnLike.setOnClickListener {
            likeViewModel.updateStationLike(arsId)

            if (selected == "1") {
                selected = "0"
                binding.btnLike.setBackgroundResource(R.drawable.unlike)
            } else {
                selected = "1"
                binding.btnLike.setBackgroundResource(R.drawable.like)
            }
        }

        setAdview()
    }

    private fun getLikeInfo(arsId: String) {
        // 상단 즐겨찾기 아이콘 세팅
        /*likeViewModel.getLikeStationList()
        likeViewModel.likeStationList.observe(this) {

            for (item in it) {
                if (arsId == item.ars_id) {
                    binding.btnLike.setBackgroundResource(R.drawable.like)
                    break
                } else {
                    binding.btnLike.setBackgroundResource(R.drawable.unlike)
                }
            }
        }*/
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(
                Int.MAX_VALUE
            )) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    private fun setAdview() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    companion object {
        const val ACTION_START = "start"
        const val ACTION_STOP = "stop"
    }
}