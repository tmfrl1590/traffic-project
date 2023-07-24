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
import com.system.traffic.dataModel.LineModel
import com.system.traffic.db.entity.LineEntity
import com.system.traffic.main.LineStationActivity

class LineAdapter(val context : Context, val lineList : ArrayList<LineEntity>, val list : ArrayList<LineModel>) : RecyclerView.Adapter<LineAdapter.ViewHolder>() {

    interface ItemClick {
        fun onClick(view : View, position: Int)
    }
    var itemClick : ItemClick? = null

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val holderLineView : LinearLayout = view.findViewById(R.id.holder_line_view)
        val lineName : TextView = view.findViewById(R.id.lineName)
        val dirName : TextView = view.findViewById(R.id.dirName)
        val btnLike : ImageView = view.findViewById(R.id.btn_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_line_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnLike.setOnClickListener {v ->
            itemClick?.onClick(v, position)
        }

        holder.lineName.text = lineList[position].line_name
        holder.lineName.isSelected = true

        for(i in list){
            if(lineList[position].line_id == i.line_id){
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
        holder.dirName.text = lineList[position].dir_down_name + "  ~  " + lineList[position].dir_down_name
        holder.dirName.isSelected = true

        holder.holderLineView.setOnClickListener {
            val intent = Intent(context, LineStationActivity::class.java )
            intent.putExtra("line_id", lineList[position].line_id)
            intent.putExtra("line_name", lineList[position].line_name)
            intent.putExtra("dir_start", lineList[position].dir_up_name)
            intent.putExtra("dir_end", lineList[position].dir_down_name)
            intent.putExtra("selected", lineList[position].selected)
            intent.putExtra("first_run_time", lineList[position].first_run_time)
            intent.putExtra("last_run_time", lineList[position].last_run_time)
            intent.putExtra("run_interval", lineList[position].run_interval)

            context.startActivity(intent)
        }

        if(lineList[position].selected == "1"){
            holder.btnLike.setBackgroundResource(R.drawable.like)
        }else{
            holder.btnLike.setBackgroundResource(R.drawable.unlike)
        }
    }
}