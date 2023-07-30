package com.system.traffic.main.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.system.traffic.R
import com.system.traffic.dataModel.BusArriveModel
import com.system.traffic.dataModel.LineModel
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.main.LineStationActivity
import com.system.traffic.util.SettingUtil

class BusArriveAdapter(
    val context: Context,
    val busArriveList: List<BusArriveModel>,
    val colorList: ArrayList<LineModel>,
    val likeList: ArrayList<LineEntity>,
    val color: String,
    val busstop_id: String,
    val serviceMap : Map<String, String>
) : RecyclerView.Adapter<BusArriveAdapter.ViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface ItemClick2 {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    var itemClick2: ItemClick2? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val lineName: TextView = view.findViewById(R.id.lineName)
        val busStopName: TextView = view.findViewById(R.id.busStopName)
        val remainMin: TextView = view.findViewById(R.id.remainMin)
        val likeArea: ImageView = view.findViewById(R.id.likeArea)
        //val alarm: ImageView = view.findViewById(R.id.alarm)
        val lowBus: TextView = view.findViewById(R.id.low_bus)
        val holderLineView: LinearLayout = view.findViewById(R.id.holder_line_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_arrive_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.likeArea.setOnClickListener { v ->
            itemClick?.onClick(v, position)
        }

        /*holder.alarm.setOnClickListener { v ->
            itemClick2?.onClick(v, position)
        }*/

        holder.holderLineView.setOnClickListener {
            val intent = Intent(context, LineStationActivity::class.java)
            intent.putExtra("line_id", busArriveList[position].line_id)
            intent.putExtra("line_name", busArriveList[position].line_name)
            intent.putExtra("dir_start", busArriveList[position].dir_start)
            intent.putExtra("dir_end", busArriveList[position].dir_end)
            context.startActivity(intent)
        }


        for (i in likeList) {
            if (busArriveList[position].line_id == i.line_id) {
                holder.likeArea.setBackgroundResource(R.drawable.like)
                break
            }else{
                holder.likeArea.setBackgroundResource(R.drawable.unlike)
            }
        }

        holder.lineName.text = busArriveList[position].line_name
        holder.lineName.isSelected = true
        holder.busStopName.isSelected = true

        for (i in colorList) {
            if (busArriveList[position].line_id == i.line_id) {
                when (i.line_kind) {
                    "1" -> {
                        holder.lineName.setTextColor(Color.parseColor("#ff0000"))
                    }
                    "2" -> {
                        holder.lineName.setTextColor(Color.parseColor("#FFA500"))
                    }
                    "3" -> {
                        holder.lineName.setTextColor(Color.parseColor("#228B22"))
                    }
                }
            }
        }

        holder.busStopName.text =
            "현재 : " + busArriveList[position].busstop_name + " ( " + busArriveList[position].remain_stop + " 정거장 전 )"

        if (busArriveList[position].arrive_flag == "1") {
            holder.remainMin.text = "곧 도착"
        } else {
            holder.remainMin.text = busArriveList[position].remain_min + "분"
        }


        when (color) {
            SettingUtil.RED -> {
                holder.remainMin.setTextColor(Color.parseColor("#ff0000"))
            }
            SettingUtil.YELLOW -> {
                holder.remainMin.setTextColor(Color.parseColor("#FFC107"))
            }
            SettingUtil.GREEN -> {
                holder.remainMin.setTextColor(Color.parseColor("#00ff00"))
            }
            SettingUtil.BLUE -> {
                holder.remainMin.setTextColor(Color.parseColor("#0000ff"))
            }
        }


        if (busArriveList[position].low_bus == "1") {
            holder.lowBus.visibility = View.VISIBLE
        } else {
            holder.lowBus.visibility = View.INVISIBLE
        }

        /*if(serviceMap["alarmStation"] == busstop_id && serviceMap["alarmLine"] == busArriveList[position].line_name){
            holder.alarm.setBackgroundResource(R.drawable.alarm_set)
        }else{
            holder.alarm.setBackgroundResource(R.drawable.alarm)
        }*/
    }

    override fun getItemCount(): Int {
        return busArriveList.size
    }
}