package com.system.traffic.main.adapter


import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.system.traffic.R
import com.system.traffic.util.SettingUtil

@BindingAdapter("isLike")
fun ImageView.setIsLike(isLike: Boolean){
    setImageResource(
        if(isLike) R.drawable.like
        else R.drawable.unlike
    )
}

@BindingAdapter("lineNameColor")
fun TextView.setTextColor(line_kind: String){
    when(line_kind){
        "1" -> {
            setTextColor(Color.parseColor("#ff0000"))
        }
        "2" -> {
            setTextColor(Color.parseColor("#FFA500"))
        }
        "3" -> {
            setTextColor(Color.parseColor("#228B22"))
        }
    }
}


@BindingAdapter("arriveColor")
fun TextView.arriveColor(arriveColor: String){
    when(arriveColor){
        SettingUtil.RED -> {
            setTextColor(Color.parseColor("#ff0000"))
        }
        SettingUtil.YELLOW -> {
            setTextColor(Color.parseColor("#FFC107"))
        }
        SettingUtil.GREEN -> {
            setTextColor(Color.parseColor("#00ff00"))
        }
        SettingUtil.BLUE -> {
            setTextColor(Color.parseColor("#0000ff"))
        }
    }
}
