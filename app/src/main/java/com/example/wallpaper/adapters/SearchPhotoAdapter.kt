package com.example.wallpaper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.bumptech.glide.load.engine.cache.MemoryCache
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ItemRvBinding
import com.example.wallpaper.models.s.Result

class SearchPhotoAdapter(var listener:onItemClickListener) : PagingDataAdapter<Result, SearchPhotoAdapter.Vh>(MyDifUtill()) {

    class MyDifUtill : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(photoData: Result) {
            itemRvBinding.image.load(photoData.urls.regular) {
                placeholder(R.drawable.ic_launcher_background)
                memoryCachePolicy(CachePolicy.WRITE_ONLY)
                crossfade(300)
                itemView.setOnClickListener {
                    listener.OnItemClick(photoData)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    interface onItemClickListener {
       fun OnItemClick(result: Result)
    }
}