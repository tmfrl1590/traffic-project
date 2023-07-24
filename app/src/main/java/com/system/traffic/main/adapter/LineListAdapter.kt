package com.system.traffic.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.system.traffic.R
import com.system.traffic.databinding.HolderLineListBinding
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.main.Handler1

class LineListAdapter(private val handler: Handler1): ListAdapter<LineEntity, LineListAdapter.ContentViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            HolderLineListBinding.inflate(LayoutInflater.from(parent.context), parent, false), handler
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<LineEntity>() {
            override fun areItemsTheSame(oldItem: LineEntity, newItem: LineEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LineEntity, newItem: LineEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ContentViewHolder(private val binding: HolderLineListBinding, private val handler: Handler1): RecyclerView.ViewHolder(binding.root){
        fun bind(item: LineEntity){
            binding.item = item
            binding.handler = handler
            when(item.line_kind){
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

            if(item.selected == "1"){
                binding.btnLike.setImageResource(R.drawable.like)
            }else{
                binding.btnLike.setImageResource(R.drawable.unlike)
            }
        }
    }
}