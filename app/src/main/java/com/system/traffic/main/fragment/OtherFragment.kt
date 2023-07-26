package com.system.traffic.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.system.traffic.BuildConfig
import com.system.traffic.R
import com.system.traffic.databinding.FragmentOtherBinding
import com.system.traffic.main.other.SuggestActivity
import com.system.traffic.main.viewModel.DataStoreViewModel
import com.system.traffic.main.viewModel.MainViewModel
import com.system.traffic.main.viewModel.OtherViewModel
import com.system.traffic.util.ColorUtil


class OtherFragment : Fragment() {

    private var _binding : FragmentOtherBinding? = null
    private val binding get() = _binding!!

    private val otherViewModel: OtherViewModel by viewModels()
    private val dataStoreViewModel : DataStoreViewModel by viewModels()

    private var color : String = ""
    //private var reloadTime : String = ""

    private var selectedColor : String = ""
    //private var selectedTime : String = "30000" // 30초

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentOtherBinding.inflate(inflater, container, false).apply {
            _binding = this
            lifecycleOwner = viewLifecycleOwner
            otherViewModel = this@OtherFragment.otherViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdview()
        initView()

        otherViewModel.intentSuggestActivity.observe(viewLifecycleOwner){
            val intent = Intent(requireContext(), SuggestActivity::class.java)
            startActivity(intent)
        }

        otherViewModel.setArriveColor.observe(viewLifecycleOwner){
            setArriveColor()
        }
    }

    /*fun setReloadTime(){
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(requireContext()).inflate( R.layout.layout_bottom_time, null)
        bottomSheetView.findViewById<View>(R.id.btn_close).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<View>(R.id.time_10).setOnClickListener{
            selectedTime = "10000"
            dataStoreViewModel.setAlarmReloadTime(selectedTime)
            binding.reloadTime.text = "10초"
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<View>(R.id.time_20).setOnClickListener{
            selectedTime = "20000"
            dataStoreViewModel.setAlarmReloadTime(selectedTime)
            binding.reloadTime.text = "20초"
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<View>(R.id.time_30).setOnClickListener{
            selectedTime = "30000"
            dataStoreViewModel.setAlarmReloadTime(selectedTime)
            binding.reloadTime.text ="30초"
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<View>(R.id.time_60).setOnClickListener{
            selectedTime = "60000"
            dataStoreViewModel.setAlarmReloadTime(selectedTime)
            binding.reloadTime.text = "60초"
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }*/

    fun setArriveColor(){
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(requireContext()).inflate( R.layout.layout_bottom_color, null)
        bottomSheetView.findViewById<View>(R.id.btn_close).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<View>(R.id.bottom_red).setOnClickListener{
            selectedColor = ColorUtil.RED
            dataStoreViewModel.setArriveColor(selectedColor)
            binding.arriveColor.text = ColorUtil.RED
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<View>(R.id.bottom_blue).setOnClickListener{
            selectedColor = ColorUtil.BLUE
            dataStoreViewModel.setArriveColor(selectedColor)
            binding.arriveColor.text = ColorUtil.BLUE
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<View>(R.id.bottom_green).setOnClickListener{
            selectedColor = ColorUtil.GREEN
            dataStoreViewModel.setArriveColor(selectedColor)
            binding.arriveColor.text = ColorUtil.GREEN
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<View>(R.id.bottom_yellow).setOnClickListener{
            selectedColor = ColorUtil.YELLOW
            dataStoreViewModel.setArriveColor(selectedColor)
            binding.arriveColor.text = ColorUtil.YELLOW
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun initView(){

        binding.appVersion.text = BuildConfig.VERSION_NAME

        // 버스 도착 시간 색상 가져오기
        dataStoreViewModel.getArriveColor()
        dataStoreViewModel.resultArriveColor.observe(viewLifecycleOwner){
            color = it
            binding.arriveColor.text = color
        }

        /*dataStoreViewModel.getAlarmReloadTime()
        dataStoreViewModel.resultAlarmReloadTime.observe(viewLifecycleOwner){
            reloadTime = it
            when(reloadTime){
                "10000" -> binding.reloadTime.text  = "10초"
                "20000" -> binding.reloadTime.text  = "20초"
                "30000" -> binding.reloadTime.text  = "30초"
                "60000" -> binding.reloadTime.text  = "60초"
            }
        }*/
    }

    private fun setAdview(){
        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}