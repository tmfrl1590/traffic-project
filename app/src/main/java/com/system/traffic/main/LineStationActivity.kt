package com.system.traffic.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.system.traffic.R
import com.system.traffic.databinding.ActivityLineStationBinding
import com.system.traffic.main.adapter.LineStationInfoListAdapter
import com.system.traffic.main.viewModel.LikeViewModel
import com.system.traffic.main.viewModel.MainViewModel

class LineStationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLineStationBinding

    private val mainViewModel : MainViewModel by viewModels()
    private val likeViewModel : LikeViewModel by viewModels()

    private val adapter: LineStationInfoListAdapter by lazy {
        LineStationInfoListAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLineStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initEvent()
        setAdview()
    }

    private fun initEvent(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initView(){

        val lineId = intent.getStringExtra("line_id")!!

        mainViewModel.getLineStationInfo(lineId)
        mainViewModel.resultLineStationInfo.observe(this@LineStationActivity){
            binding.firstRunTime.text = "첫차시간\n${it.first_run_time ?: "a"}"
            binding.lastRunTime.text = "막차시간\n${it.last_run_time ?: "b"}"
            binding.runInterval.text = "운행간격\n${it.run_interval ?: "c"} 분"

            binding.lineName.text = it.line_name
            binding.direction.text = "${it.dir_down_name} ~ ${it.dir_up_name}"

            if(it.selected){
                binding.btnLike.setBackgroundResource(R.drawable.like)
            }else{
                binding.btnLike.setBackgroundResource(R.drawable.unlike)
            }
        }


        binding.btnLike.setOnClickListener {
            likeViewModel.updateLineLike(lineId)
        }


        mainViewModel.getLineStationInfoList(lineId)
        mainViewModel.resultLineStationInfoList.observe(this){
            binding.rvLineStationInfo.adapter = adapter
            binding.rvLineStationInfo.layoutManager = LinearLayoutManager(this)
            adapter.submitList(it)
        }
    }

    private fun setAdview(){
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }
}