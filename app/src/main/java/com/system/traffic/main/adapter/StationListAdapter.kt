package com.system.traffic.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.system.traffic.R
import com.system.traffic.databinding.HolderStationListBinding
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.main.Handler1


class StationListAdapter(private val handler: Handler1) : androidx.recyclerview.widget.ListAdapter<StationEntity, StationListAdapter.ContentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            HolderStationListBinding.inflate(LayoutInflater.from(parent.context), parent, false), handler
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<StationEntity>() {
            override fun areItemsTheSame(oldItem: StationEntity, newItem: StationEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StationEntity, newItem: StationEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ContentViewHolder(private val binding: HolderStationListBinding, private val handler: Handler1) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: StationEntity){
            binding.item = item
            binding.handler = handler

            /*if(item.selected){
                binding.btnLike.setImageResource(R.drawable.like)
            }else{
                binding.btnLike.setImageResource(R.drawable.unlike)
            }*/
        }
    }

}