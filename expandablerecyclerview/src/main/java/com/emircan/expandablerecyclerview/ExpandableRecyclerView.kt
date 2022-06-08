package com.emircan.expandablerecyclerview

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExpandableRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var isSingleExpandItem: Boolean
    private var keepExpandCollapseState: Boolean

    init {
        layoutManager = LinearLayoutManager(context)
        context.obtainStyledAttributes(
            attrs,
            R.styleable.ExpandableRecyclerView,
        ).run {
            isSingleExpandItem =
                getBoolean(R.styleable.ExpandableRecyclerView_singleExpandItem, true)
            keepExpandCollapseState =
                getBoolean(R.styleable.ExpandableRecyclerView_keepExpandCollapseState, true)
            recycle()
        }
    }

    fun setData(
        @LayoutRes layoutId: Int,
        list: List<ExpandableItem>,
        isSingleExpandItem: Boolean = this.isSingleExpandItem,
        keepExpandCollapseState: Boolean = this.keepExpandCollapseState
    ) {
        this.isSingleExpandItem = isSingleExpandItem
        this.keepExpandCollapseState = keepExpandCollapseState
        adapter =
            ExpandableRecyclerAdapter(layoutId, list, isSingleExpandItem, keepExpandCollapseState)
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(KEY_SUPER_SAVE_STATE, super.onSaveInstanceState())
            (adapter as? ExpandableRecyclerAdapter)?.let {
                putIntegerArrayList(KEY_EXPAND_ITEM_POSITIONS, it.expandableItemPositions)
            }
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val superState: Parcelable? = (state as? Bundle)?.run {
            (adapter as? ExpandableRecyclerAdapter)?.expandableItemPositions =
                getIntegerArrayList(KEY_EXPAND_ITEM_POSITIONS) ?: arrayListOf()
            getParcelable<Parcelable>(KEY_SUPER_SAVE_STATE)
        } ?: state
        super.onRestoreInstanceState(superState)
    }

    companion object {
        private const val KEY_SUPER_SAVE_STATE = "KEY_SUPER_SAVE_STATE"
        private const val KEY_EXPAND_ITEM_POSITIONS = "KEY_EXPAND_ITEM_POSITIONS"
    }
}
