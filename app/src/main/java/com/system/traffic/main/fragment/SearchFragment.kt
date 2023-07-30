package com.system.traffic.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.system.traffic.R
import com.system.traffic.dataModel.LineModel
import com.system.traffic.databinding.FragmentSearchBinding
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.main.BusArriveActivity
import com.system.traffic.main.Handler1
import com.system.traffic.main.LineStationActivity
import com.system.traffic.main.adapter.*
import com.system.traffic.main.viewModel.MainViewModel
import com.system.traffic.main.viewModel.LikeViewModel

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel : MainViewModel by activityViewModels()
    private val likeViewModel : LikeViewModel by activityViewModels()

    private var selectedCategory : String = "정류장"

    //private lateinit var stationAdapter : StationAdapter
    //private lateinit var lineAdapter : LineAdapter


    private val adapter1: StationListAdapter by lazy { StationListAdapter(Handler(likeViewModel)) }
    private val adapter2: LineListAdapter by lazy { LineListAdapter(Handler(likeViewModel)) }

    private val stationList = ArrayList<StationEntity>()
    private val lineList = ArrayList<LineEntity>()

    private lateinit var busColorList : ArrayList<LineModel>


    private var resultText : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        getLineColor()
        initView()
        initEvent()

        return binding.root
    }

    private fun getLineColor(){
        mainViewModel.getLineColor()
        mainViewModel.resultLineColorList.observe(viewLifecycleOwner) {
            busColorList = it
        }
    }

    private fun initView(){
        setCategoryBottomSheet()
        setAdview()
    }

    private fun initEvent(){

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { // 검색버튼 누를때
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { // 글자 변경이 일어날때마다
                if(newText!!.isNotEmpty()){
                    resultText = "%$newText%"
                    if(resultText.isNotEmpty()){
                        if(binding.selectStationLine.text == "정류장"){

                            mainViewModel.getSearchedStationList(resultText)
                            mainViewModel.resultStationList.observe(viewLifecycleOwner, Observer {
                                stationList.clear()

                                for(item in it){
                                    stationList.add(item)
                                }

                                setStationRV()
                            })

                        } else {
                            mainViewModel.getSearchedLineList(resultText)
                            mainViewModel.resultLineList.observe(viewLifecycleOwner, Observer {
                                lineList.clear()

                                for(item in it){
                                    lineList.add(item)
                                }

                                setLineRV()
                            })
                        }
                    }
                }

                return true
            }

        })
    }

    private fun setCategoryBottomSheet(){
        // BottomSheetDialog
        binding.selectStationLine.setOnClickListener {
            // bottomSheetDialog 객체 생성
            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)

            // layout_bottom_sheet를 뷰 객체로 생성
            val bottomSheetView = LayoutInflater.from(requireContext()).inflate( R.layout.layout_bottom_sheet, null)

            // bottomSheetDialog의 dismiss 버튼 선택시 dialog disappear
            bottomSheetView.findViewById<View>(R.id.btn_close).setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<View>(R.id.selectedStation).setOnClickListener {
                selectedCategory = bottomSheetView.findViewById<TextView>(R.id.selectedStation).text.toString()
                binding.selectStationLine.text = selectedCategory
                binding.searchView.setQuery("", false)
                stationList.clear()
                lineList.clear()
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<View>(R.id.selectedLine).setOnClickListener {
                selectedCategory = bottomSheetView.findViewById<TextView>(R.id.selectedLine).text.toString()
                binding.selectStationLine.text = selectedCategory
                binding.searchView.setQuery("", false)
                stationList.clear()
                lineList.clear()
                bottomSheetDialog.dismiss()
            }

            // bottomSheetDialog 뷰 생성
            bottomSheetDialog.setContentView(bottomSheetView)

            // bottomSheetDialog 호출
            bottomSheetDialog.show()
        }
    }

    private fun setAdview(){
        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setStationRV(){
        binding.rvStationList.adapter = adapter1
        binding.rvStationList.layoutManager = LinearLayoutManager(requireContext())
        adapter1.submitList(stationList)
    }

    private fun setLineRV(){
        binding.rvStationList.adapter = adapter2
        binding.rvStationList.layoutManager = LinearLayoutManager(requireContext())
        adapter2.submitList(lineList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    inner class Handler(private val likeViewModel: LikeViewModel): Handler1 {
        override fun onStationClick(stationEntity: StationEntity) {
            likeViewModel.updateStation(stationEntity)
            adapter1.notifyDataSetChanged()
        }

        override fun onLineClick(lineEntity: LineEntity) {
            likeViewModel.updateLine(lineEntity)
            adapter2.notifyDataSetChanged()
        }

        override fun intentBusArriveActivity(stationEntity: StationEntity) {
            val intent = Intent(requireContext(), BusArriveActivity::class.java).apply {
                putExtra("busstop_id", stationEntity.busstop_id )
                putExtra("ars_id", stationEntity.ars_id )
            }
            startActivity(intent)
        }

        override fun intentLineStationActivity(lineEntity: LineEntity) {
            val intent = Intent(requireContext(), LineStationActivity::class.java).apply {
                putExtra("line_id", lineEntity.line_id)
                putExtra("line_name", lineEntity.line_name)
                putExtra("dir_start", lineEntity.dir_up_name)
                putExtra("dir_end", lineEntity.dir_down_name)
                putExtra("first_run_time", lineEntity.first_run_time)
                putExtra("last_run_time", lineEntity.last_run_time)
                putExtra("run_interval", lineEntity.run_interval)
                putExtra("selected", lineEntity.selected)
            }
            startActivity(intent)
        }
    }
}