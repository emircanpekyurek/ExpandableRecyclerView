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

    private var isSingleExpandItem: Boolean = true

    init {
        layoutManager = LinearLayoutManager(context)
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ExpandableRecyclerView,
        ).run {
            isSingleExpandItem = getBoolean(R.styleable.ExpandableRecyclerView_singleExpandItem, true)
            recycle()
        }
    }

    fun setData(
        @LayoutRes layoutId: Int,
        list: List<ExpandableItem>,
        singleExpandItem: Boolean = isSingleExpandItem
    ) {
        isSingleExpandItem = singleExpandItem
        adapter = ExpandableRecyclerAdapter(layoutId, list, singleExpandItem)
    }
}
