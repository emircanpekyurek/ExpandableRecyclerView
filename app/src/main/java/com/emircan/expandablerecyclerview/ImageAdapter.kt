package com.emircan.expandablerecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(
    private val size: Int = 6,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onItemClick.invoke(position) }
    }

    override fun getItemCount(): Int = size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}