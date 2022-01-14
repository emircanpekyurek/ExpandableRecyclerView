package com.emircan.expandablerecyclerview

import androidx.recyclerview.widget.RecyclerView

data class ExpandableItem(
    val title: String,
    val adapter: RecyclerView.Adapter<*>? = null,
    val notExpandableClickListener: (() -> Unit)? = null
)