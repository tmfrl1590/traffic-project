package com.system.traffic.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.system.traffic.R
import com.system.traffic.databinding.ActivityLineStationBinding
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.main.adapter.LineStationInfoAdapter
import com.system.traffic.main.viewModel.LikeViewModel
import com.system.traffic.main.viewModel.MainViewModel

class LineStationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLineStationBinding

    private val viewModel : MainViewModel by viewModels()
    private val likeViewModel : LikeViewModel by viewModels()

    private lateinit var lineStationAdapter : LineStationInfoAdapter

    private var selected : String = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLineStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){

        val lineId = intent.getStringExtra("line_id")!!
        val lineName = intent.getStringExtra("line_name")
        val dirStart = intent.getStringExtra("dir_start")
        val dirEnd = intent.getStringExtra("dir_end")
        val firstRunTime = intent.getStringExtra("first_run_time")
        val lastRunTime = intent.getStringExtra("last_run_time")
        val runInterval = intent.getStringExtra("run_interval")
        selected = intent.getStringExtra("selected").toString()

        binding.firstRunTime.text = "첫차시간\n$firstRunTime"
        binding.lastRunTime.text = "막차시간\n$lastRunTime"
        binding.runInterval.text = "운행간격\n$runInterval 분"

        binding.lineName.text = lineName
        binding.direction.text = "$dirEnd ~ $dirStart"


        binding.btnLike.setOnClickListener {
            likeViewModel.updateLineLike(lineId!!)

            if(selected == "1"){
                selected = "0"
                binding.btnLike.setBackgroundResource(R.drawable.unlike)
            }else{
                selected = "1"
                binding.btnLike.setBackgroundResource(R.drawable.like)
            }
        }

        viewModel.getLineStationInfo(lineId)
        viewModel.resultLineStationInfo.observe(this){

            if(it.selected){
                binding.btnLike.setBackgroundResource(R.drawable.like)
            }else{
                binding.btnLike.setBackgroundResource(R.drawable.unlike)
            }
        }


        viewModel.getLineStationInfoList(lineId)
        viewModel.resultLineStationInfoList.observe(this){
            lineStationAdapter = LineStationInfoAdapter(this, it)
            binding.rvLineStationInfo.adapter = lineStationAdapter
            binding.rvLineStationInfo.layoutManager = LinearLayoutManager(this)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        setAdview()
    }

    private fun setAdview(){
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}