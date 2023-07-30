package com.system.traffic.main.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.system.traffic.R
import com.system.traffic.dataModel.BusArriveModel
import com.system.traffic.dataModel.LineModel
import com.system.traffic.databinding.HolderArriveListBinding
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.main.BusArriveActivity
import com.system.traffic.main.Handler1
import com.system.traffic.util.ColorUtil
import com.system.traffic.util.LikeUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BusArriveListAdapter(
        private val busColorList: ArrayList<LineModel>,
        //private val likeLineList: ArrayList<LineEntity>,
        private val handler: BusArriveActivity.Handler,
        private val arriveColor: String
    )
        : ListAdapter<BusArriveModel, BusArriveListAdapter.ContentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            HolderArriveListBinding.inflate(LayoutInflater.from(parent.context), parent, false), handler, arriveColor
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
        private val handler: BusArriveActivity.Handler,
        private val arriveColor : String,
        ): ViewHolder(binding.root){

        fun bind(item: BusArriveModel){
            binding.item = item
            binding.handler = handler

            // 버스 종류에 따라 lineName 색상 설정
            for(i in busColorList){
                if(item.line_id == i.line_id){
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

               if(item.line_id == i.line_id){
                    binding.likeArea.setBackgroundResource(R.drawable.like)
                    break
                } else{
                    binding.likeArea.setBackgroundResource(R.drawable.unlike)
                }
            }

            // 도착시간 색상 설정
            /*when (arriveColor) {
                ColorUtil.RED -> {
                    binding.remainMin.setTextColor(Color.parseColor("#ff0000"))
                }
                ColorUtil.YELLOW -> {
                    binding.remainMin.setTextColor(Color.parseColor("#FFC107"))
                }
                ColorUtil.GREEN -> {
                    binding.remainMin.setTextColor(Color.parseColor("#00ff00"))
                }
                ColorUtil.BLUE -> {
                    binding.remainMin.setTextColor(Color.parseColor("#0000ff"))
                }
            }*/
        }

    }
}