package com.system.traffic.main.adapter


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.system.traffic.R

@BindingAdapter("isLike")
fun ImageView.setIsLike(isLike: Boolean){
    setImageResource(
        if(isLike) R.drawable.like
        else R.drawable.unlike
    )
}