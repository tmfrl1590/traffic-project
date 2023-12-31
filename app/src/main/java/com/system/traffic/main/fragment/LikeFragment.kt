package com.system.traffic.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.system.traffic.dataModel.LineModel
import com.system.traffic.databinding.FragmentLikeBinding
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.main.BusArriveActivity
import com.system.traffic.main.Handler1
import com.system.traffic.main.LineStationActivity
import com.system.traffic.main.adapter.*
import com.system.traffic.main.viewModel.DataStoreViewModel
import com.system.traffic.main.viewModel.LikeViewModel
import com.system.traffic.main.viewModel.MainViewModel
import com.system.traffic.util.SettingUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LikeFragment : Fragment() {

    private var _binding : FragmentLikeBinding? = null
    private val binding get() = _binding!!

    private val likeViewModel : LikeViewModel by activityViewModels()
    private val dataStoreViewModel : DataStoreViewModel by activityViewModels()

    private val stationAdapter: StationListAdapter by lazy { StationListAdapter(Handler(likeViewModel)) }
    private val lineAdapter: LineListAdapter by lazy { LineListAdapter(Handler(likeViewModel)) }

    private var likeStationList = ArrayList<StationEntity>()
    private var likeLineList = ArrayList<LineEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArriveColor()
        setStation()
        setLine()
        setAdview()
    }

    private fun getArriveColor(){
        dataStoreViewModel.getArriveColor()
        dataStoreViewModel.resultArriveColor.observe(viewLifecycleOwner){
            SettingUtil.BUS_ARRIVE_COLOR = it
        }
    }

    private fun setAdview(){
        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setStation(){

        lifecycleScope.launch{
            likeViewModel.likeStationList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    if(it.isEmpty()){
                        binding.noStation.isVisible = true
                        binding.rvStationList.isVisible = false
                    } else{
                        binding.noStation.isVisible = false
                        binding.rvStationList.isVisible = true
                    }

                    likeStationList = it.toCollection(ArrayList())

                    setStationRV()
                }
        }
    }

    private fun setLine(){
        lifecycleScope.launch{
            likeViewModel.likeLineList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    if(it.isEmpty()){
                        binding.noLine.isVisible = true
                        binding.rvLineList.isVisible = false
                    } else{
                        binding.noLine.isVisible = false
                        binding.rvLineList.isVisible = true
                    }

                    likeLineList = it.toCollection(ArrayList())

                    setLineRV()
                }
        }
    }

    private fun setStationRV(){
        binding.rvStationList.adapter = stationAdapter
        binding.rvStationList.layoutManager = LinearLayoutManager(requireContext())
        stationAdapter.submitList(likeStationList)
    }

    private fun setLineRV(){
        binding.rvLineList.adapter = lineAdapter
        binding.rvLineList.layoutManager = LinearLayoutManager(requireContext())
        lineAdapter.submitList(likeLineList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class Handler(private val likeViewModel: LikeViewModel): Handler1 {
        override fun onStationClick(stationEntity: StationEntity) {
            likeViewModel.updateStation(stationEntity)
            stationAdapter.notifyDataSetChanged()
        }

        override fun onLineClick(lineEntity: LineEntity) {
            likeViewModel.updateLine(lineEntity)
            lineAdapter.notifyDataSetChanged()
        }

        override fun intentBusArriveActivity(stationEntity: StationEntity) {
            val intent = Intent(requireContext(), BusArriveActivity::class.java).apply {
                putExtra("busstop_id", stationEntity.busstop_id )
                putExtra("ars_id", stationEntity.ars_id )
            }
            startActivity(intent)
        }

        override fun intentLineStationActivity(lineId: String) {
            val intent = Intent(requireContext(), LineStationActivity::class.java).apply {
                putExtra("line_id", lineId)
            }
            startActivity(intent)
        }
    }
}


