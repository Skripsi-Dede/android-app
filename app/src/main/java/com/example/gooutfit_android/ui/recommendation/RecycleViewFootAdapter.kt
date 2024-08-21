package com.example.gooutfit_android.ui.recommendation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gooutfit_android.R

class RecycleViewFootAdapter(private val footwearList: List<String>) : RecyclerView.Adapter<RecycleViewFootAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = footwearList[position]
        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return footwearList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgCard)
    }
}