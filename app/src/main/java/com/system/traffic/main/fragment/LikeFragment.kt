package com.system.traffic.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.system.traffic.R
import com.system.traffic.dataModel.LineModel
import com.system.traffic.dataModel.StationModel
import com.system.traffic.databinding.FragmentLikeBinding
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.main.adapter.LineAdapter
import com.system.traffic.main.adapter.StationAdapter
import com.system.traffic.main.viewModel.LikeViewModel
import com.system.traffic.main.viewModel.MainViewModel

class LikeFragment : Fragment() {

    private var _binding : FragmentLikeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()
    private val likeViewModel : LikeViewModel by activityViewModels()

    private lateinit var stationAdapter : StationAdapter
    private lateinit var lineAdapter: LineAdapter

    private lateinit var busColorList : ArrayList<LineModel>

    private val likeStationList = ArrayList<StationEntity>()
    private val likeLineList = ArrayList<LineEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setStation()
        setLine()

    }

    private fun initView(){
        setAdview()
    }

    private fun setAdview(){
        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setStation(){
        likeViewModel.getLikeStationList()
        likeViewModel.getLikeLineList()
        likeViewModel.likeStationList.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.noStation.isVisible = true
                binding.rvStationList.isVisible = false
            } else{
                binding.noStation.isVisible = false
                binding.rvStationList.isVisible = true
            }

            likeStationList.clear()

            for(item in it){
                likeStationList.add(item)
            }

            setStationRV()
        }
    }

    private fun setLine(){
        likeViewModel.likeLineList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.noLine.isVisible = true
                binding.rvLineList.isVisible = false
            } else{
                binding.noLine.isVisible = false
                binding.rvLineList.isVisible = true
            }

            likeLineList.clear()

            for(item in it){
                likeLineList.add(item)
            }

            setLineRV()
        }
    }

    private fun setLineRV(){

        viewModel.getLineColor()
        viewModel.resultLineColorList.observe(viewLifecycleOwner){
            busColorList = it

            lineAdapter = LineAdapter(requireContext(), likeLineList, busColorList)
            binding.rvLineList.adapter = lineAdapter
            binding.rvLineList.layoutManager = LinearLayoutManager(requireContext())

            lineAdapter.itemClick = object : LineAdapter.ItemClick{
                override fun onClick(view: View, position: Int) {
                    likeViewModel.updateLine(likeLineList[position])
                }
            }
        }
    }

    private fun setStationRV(){
        stationAdapter = StationAdapter(requireContext(), likeStationList)
        binding.rvStationList.adapter = stationAdapter
        binding.rvStationList.layoutManager = LinearLayoutManager(requireContext())

        stationAdapter.itemClick = object : StationAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                likeViewModel.updateStation(likeStationList[position])
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}