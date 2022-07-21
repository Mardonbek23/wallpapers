package com.example.wallpaper.other

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.wallpaper.R

class MyHelper {
    fun ImageView.setImageByUrl(url:String){
        Glide.with(this).load(url)
            .placeholder(R.drawable.defimage)
            .into(this)
    }
}