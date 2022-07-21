package com.example.wallpaper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4kw.models.PhotoData
import com.example.wallpaper.R
import com.example.wallpaper.databinding.ItemRvBinding

class RvAdapterLiked(var list: ArrayList<PhotoData>, var listener: OnClickListener) :
    RecyclerView.Adapter<RvAdapterLiked.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.itemRvBinding.image.setOnClickListener {
            listener.OnItemClick(list[position])
        }
        Glide.with(holder.itemView.context).load(list[position].urls.regular)
            .placeholder(R.drawable.defimage)
            .into(holder.itemRvBinding.image)
    }

    fun addPhotos(photos: ArrayList<PhotoData>) {
        val lastCount = list.size
        list.addAll(photos)
        notifyItemRangeInserted(lastCount, list.size)
        notifyItemRangeChanged(lastCount, list.size)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener {
        fun OnItemClick(photoData: PhotoData)
    }

}