package com.emircan.expandablerecyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExpandableRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = LinearLayoutManager(context)
    }

    fun setData(
        @LayoutRes layoutId: Int,
        list: List<ExpandableItem>,
        singleExpandItem: Boolean = true
    ) {
        adapter = ExpandableRecyclerAdapter(layoutId, list, singleExpandItem)
    }
}
