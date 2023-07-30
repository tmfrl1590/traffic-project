package com.system.traffic.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.system.traffic.R
import com.system.traffic.dataModel.BusArriveModel
import com.system.traffic.dataModel.LineModel
import com.system.traffic.databinding.HolderArriveListBinding
import com.system.traffic.main.BusArriveActivity
import com.system.traffic.util.LikeUtil
import com.system.traffic.util.SettingUtil

class BusArriveListAdapter(
        private val busColorList: ArrayList<LineModel>,
        private val handler: BusArriveActivity.Handler
    )
        : ListAdapter<BusArriveModel, BusArriveListAdapter.ContentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            HolderArriveListBinding.inflate(LayoutInflater.from(parent.context), parent, false), handler
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BusArriveModel>() {
            override fun areItemsTheSame(oldItem: BusArriveModel, newItem: BusArriveModel): Boolean {
                return oldItem.line_id == newItem.line_id
            }

            override fun areContentsTheSame(oldItem: BusArriveModel, newItem: BusArriveModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ContentViewHolder(
        private val binding: HolderArriveListBinding,
        private val handler: BusArriveActivity.Handler
        ): ViewHolder(binding.root){

        fun bind(busArriveModel: BusArriveModel){
            binding.busArriveModel = busArriveModel
            binding.handler = handler
            binding.settingUtil = SettingUtil

            // 버스 종류에 따라 lineName 색상 설정
            for(i in busColorList){
                if(busArriveModel.line_id == i.line_id){
                    when (i.line_kind) {
                        "1" -> {
                            binding.lineName.setTextColor(Color.parseColor("#ff0000"))
                        }
                        "2" -> {
                            binding.lineName.setTextColor(Color.parseColor("#FFA500"))
                        }
                        "3" -> {
                            binding.lineName.setTextColor(Color.parseColor("#228B22"))
                        }
                    }
                }
            }

            // 즐겨찾기 버스가 있으면 아이콘 세팅
            for(i in LikeUtil.likeLineList){

               if(busArriveModel.line_id == i.line_id){
                    binding.likeArea.setBackgroundResource(R.drawable.like)
                    break
                } else{
                    binding.likeArea.setBackgroundResource(R.drawable.unlike)
                }
            }
        }

    }
}