package com.example.wallpaper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.a4kw.models.PhotoData
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ItemRvBinding

class PhotoAdapter : PagingDataAdapter<PhotoData, PhotoAdapter.Vh>(MyDifUtill()) {

    class MyDifUtill : DiffUtil.ItemCallback<PhotoData>() {
        override fun areItemsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(photoData: PhotoData) {
//            Picasso.get().load(photoData.urls.regular)
//                .placeholder(R.drawable.ic_launcher_background).into(itemRvBinding.image)
            itemRvBinding.image.load(photoData.urls.regular) {
                placeholder(R.drawable.ic_launcher_background)
                crossfade(500)
            }
        }
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


}