package com.system.traffic.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.system.traffic.dataModel.LineStationModel
import com.system.traffic.databinding.HolderLineStationBinding

class LineStationInfoListAdapter: ListAdapter<LineStationModel, LineStationInfoListAdapter.ContentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineStationInfoListAdapter.ContentViewHolder {
        return ContentViewHolder(
            HolderLineStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object :DiffUtil.ItemCallback<LineStationModel>(){
            override fun areItemsTheSame(oldItem: LineStationModel, newItem: LineStationModel): Boolean {
                return oldItem.line_id == newItem.line_id
            }

            override fun areContentsTheSame(oldItem: LineStationModel, newItem: LineStationModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ContentViewHolder(private val binding: HolderLineStationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: LineStationModel){
            binding.item = item
        }
    }
}