package com.emircan.expandablerecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class ExpandableRecyclerAdapter(
    @LayoutRes private val layoutId: Int,
    private val list: List<ExpandableItem>,
    private val singleExpandItem: Boolean = true
) : RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder>() {

    private var expandItemPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false) as ExpandableItemView
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val view: ExpandableItemView) : RecyclerView.ViewHolder(view) {

        fun bind(item: ExpandableItem, position: Int) {
            if (expandItemPosition == position) {
                view.expand(false)
            } else {
                view.collapse(false)
            }
            view.setTitle(item.title)
            view.setData(item.adapter)
            if (singleExpandItem) {
                view.setOnClickListener {
                    if (view.isExpanded.not()) {
                        if (expandItemPosition != position) {
                            expandItemPosition?.let { expandPosition ->
                                expandItemPosition = expandPosition
                                notifyItemChanged(expandPosition)
                            }
                        }
                        expandItemPosition = position
                    } else {
                        expandItemPosition = null
                    }
                    view.toggle()
                }
            }
        }
    }
}