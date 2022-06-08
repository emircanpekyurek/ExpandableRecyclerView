package com.emircan.expandablerecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class ExpandableRecyclerAdapter(
    @LayoutRes private val layoutId: Int,
    private val list: List<ExpandableItem>,
    private val singleExpandItem: Boolean = true,
    private val keepExpandCollapseState: Boolean = true
) : RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder>() {

    private var expandItemPosition: Int? = null
    var expandableItemPositions: ArrayList<Int>
        get() = ArrayList(list.mapIndexedNotNull { index, expandableItem -> if (expandableItem.isExpanded) index else null })
        set(value) {
            value.forEach { index ->
                if (singleExpandItem) {
                    expandItemPosition = index
                }
                list.getOrNull(index)?.isExpanded = true
                notifyItemChanged(index)
            }
        }

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
            if (keepExpandCollapseState && (expandItemPosition == position || item.isExpanded)) {
                view.expand(false)
                item.isExpanded = true
            }
            if (item.isExpanded.not() || (singleExpandItem && expandItemPosition != position)) {
                view.collapse(false)
                item.isExpanded = false
            }
            view.setTitle(item.title)
            view.setArrowVisibility(item.notExpandableClickListener == null)

            if (item.adapter != null) {
                initExpandableRow(item, position)
            } else if (item.notExpandableClickListener != null) {
                initNotExpandableRow(item)
            }
        }

        private fun initExpandableRow(item: ExpandableItem, position: Int) {
            view.setData(item.adapter)
            view.setOnClickListener {
                onExpandableItemClicked(item, position)
            }
        }

        private fun onExpandableItemClicked(item: ExpandableItem, position: Int) {
            if (singleExpandItem) {
                if (view.isExpanded.not()) {
                    if (expandItemPosition != position) {
                        expandItemPosition?.let { expandPosition ->
                            expandItemPosition = position
                            notifyItemChanged(expandPosition)
                        }
                    }
                    expandItemPosition = position
                } else {
                    expandItemPosition = null
                }
            }
            view.toggle()
            item.isExpanded = view.isExpanded
        }

        private fun initNotExpandableRow(item: ExpandableItem) {
            view.setData(null)
            view.setOnClickListener {
                item.notExpandableClickListener?.invoke()
            }
        }
    }
}