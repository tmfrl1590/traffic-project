package com.system.traffic.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.system.traffic.R
import com.system.traffic.db.entity.StationEntity
import com.system.traffic.main.BusArriveActivity

class StationAdapter(val context : Context, val stationList : List<StationEntity>) : RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    interface ItemClick {
        fun onClick(view : View, position: Int)
    }
    var itemClick : ItemClick? = null

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val holderStationView : LinearLayout = view.findViewById(R.id.holder_station_view)
        val busStopName : TextView = view.findViewById(R.id.busStopName)
        val nextBusStop : TextView = view.findViewById(R.id.nextBusStop)
        val arsId : TextView = view.findViewById(R.id.arsId)
        val btnLike : ImageView = view.findViewById(R.id.btn_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_station_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnLike.setOnClickListener {v ->
            itemClick?.onClick(v, position)
        }

        holder.busStopName.text = stationList[position].busstop_name
        holder.busStopName.isSelected = true
        holder.nextBusStop.text = stationList[position].next_busstop + " 방면"
        holder.nextBusStop.isSelected = true
        holder.arsId.text = stationList[position].ars_id

        holder.holderStationView.setOnClickListener {
            val intent = Intent(context, BusArriveActivity::class.java )
            intent.putExtra("ars_id", stationList[position].ars_id)
            context.startActivity(intent)
        }

        if(stationList[position].selected == "1"){
            holder.btnLike.setBackgroundResource(R.drawable.like)
        }else{
            holder.btnLike.setBackgroundResource(R.drawable.unlike)
        }
    }

    override fun getItemCount(): Int {
        return stationList.size
    }
}