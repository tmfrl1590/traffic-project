package com.system.traffic.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.system.traffic.R
import com.system.traffic.dataModel.LineStationModel
import com.system.traffic.main.BusArriveActivity

class LineStationInfoAdapter(val context : Context, val list : List<LineStationModel>) : RecyclerView.Adapter<LineStationInfoAdapter.ViewHolder>() {

    interface ItemClick {
        fun onClick(view : View, position: Int)
    }
    var itemClick : ItemClick? = null

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val holderLineView : LinearLayout = view.findViewById(R.id.holder_line_view)
        val busStopName : TextView = view.findViewById(R.id.busStopName)
        val arsId : TextView = view.findViewById(R.id.arsId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_line_station, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.holderLineView.setOnClickListener {v ->
            itemClick?.onClick(v, position)
        }

        holder.busStopName.text = list[position].busstop_name
        holder.arsId.text = list[position].ars_id

        holder.holderLineView.setOnClickListener {
            val intent = Intent(context, BusArriveActivity::class.java )
            intent.putExtra("ars_id", list[position].ars_id)
            context.startActivity(intent)
        }
    }
}