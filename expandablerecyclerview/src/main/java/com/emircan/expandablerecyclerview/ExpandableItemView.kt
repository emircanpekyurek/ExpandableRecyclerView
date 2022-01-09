package com.emircan.expandablerecyclerview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ExpandableItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyle: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyle) {

    private val lineView: View by lazy { findViewById(R.id.lineView) }
    private val tvTitle: AppCompatTextView by lazy { findViewById(R.id.tvTitle) }
    private val ivArrow: AppCompatImageView by lazy { findViewById(R.id.ivArrow) }
    val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }

    @DrawableRes
    private var collapsedIconId = R.drawable.expandable_recycler_view_arrow_down

    @DrawableRes
    private var expandedIconId = R.drawable.expandable_recycler_view_arrow_up

    @ColorInt
    private var expandedIconTint: Int? = null

    @ColorInt
    private var collapsedIconTint: Int? = null

    private var showLine: Boolean = false
    private var animationDuration: Long = 0

    var isExpanded: Boolean = false

    init {
        inflate(context, R.layout.item_expandable_recyclerview, this)

        context.obtainStyledAttributes(
            attrs,
            R.styleable.ExpandableItemView,
            defStyleAttr,
            defStyle
        ).run {
            collapsedIconId = getResourceId(R.styleable.ExpandableItemView_collapsed_icon, R.drawable.expandable_recycler_view_arrow_down)
            expandedIconId = getResourceId(R.styleable.ExpandableItemView_expanded_icon, R.drawable.expandable_recycler_view_arrow_up)

            if (hasValue(R.styleable.ExpandableItemView_expanded_icon_tint)) {
                expandedIconTint = getColor(R.styleable.ExpandableItemView_expanded_icon_tint, 0)
            }

            if (hasValue(R.styleable.ExpandableItemView_collapsed_icon_tint)) {
                collapsedIconTint = getColor(R.styleable.ExpandableItemView_collapsed_icon_tint, 0)
            }

            if (hasValue(R.styleable.ExpandableItemView_title_color)) {
                tvTitle.setTextColor(getColor(R.styleable.ExpandableItemView_title_color, 0))
            }

            if (hasValue(R.styleable.ExpandableItemView_line_color)) {
                lineView.setBackgroundColor(getColor(R.styleable.ExpandableItemView_line_color, 0))
            }

            setTitle(getString(R.styleable.ExpandableItemView_title).orEmpty())


            if (hasValue(R.styleable.TextAppearance_android_textSize)) {
                val size = getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 15).toFloat()
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            }

            showLine = getBoolean(R.styleable.ExpandableItemView_show_view_line, false)
            animationDuration = getInt(R.styleable.ExpandableItemView_animation_duration, 0).toLong()

            val orientation = getInt(
                R.styleable.ExpandableItemView_android_orientation,
                LinearLayoutManager.VERTICAL
            )

            val spanCount = getInt(R.styleable.ExpandableItemView_recycler_span_count, 1)

            setLayoutManager(
                getInt(R.styleable.ExpandableItemView_recycler_layout_manager, -1),
                orientation,
                spanCount
            )

            recyclerView.setPadding(getDimension(R.styleable.ExpandableItemView_recycler_padding, 0F).toInt())

            recycle()
        }

        collapse(false)
        tvTitle.setOnClickListener { toggle() }
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    private fun setLayoutManager(id: Int, orientation: Int, spanCount: Int) {
        recyclerView.layoutManager = when (id) {
            0 -> LinearLayoutManager(context, orientation, false)
            1 -> GridLayoutManager(context, spanCount)
            2 -> StaggeredGridLayoutManager(spanCount, orientation)
            else -> LinearLayoutManager(context, orientation, false)
        }
    }

    fun setData(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    fun toggle() {
        if (isExpanded) {
            collapse()
        } else {
            expand()
        }
    }

    fun expand(showAnimation: Boolean = isAnimationEnabled()) {
        isExpanded = true
        setTitleFont(Typeface.DEFAULT_BOLD)
        setIconTint(expandedIconTint)
        setIcon(expandedIconId)
        expandRecyclerView(showAnimation)
    }

    fun collapse(showAnimation: Boolean = isAnimationEnabled()) {
        isExpanded = false
        setTitleFont(Typeface.DEFAULT)
        setIconTint(collapsedIconTint)
        setIcon(collapsedIconId)
        collapseRecyclerView(showAnimation)
    }

    private fun setTitleFont(typeFace: Typeface) {
        tvTitle.typeface = typeFace
    }

    private fun setIconTint(@ColorInt colorId: Int?) {
        colorId?.let { ivArrow.imageTintList = ColorStateList.valueOf(it) }
    }

    private fun setIcon(@DrawableRes drawableId: Int) {
        ivArrow.setImageDrawable(ContextCompat.getDrawable(context, drawableId))
    }

    private fun collapseRecyclerView(showAnimation: Boolean = isAnimationEnabled()) {
        if (showAnimation) {
            hideRecyclerViewWithAnimation()
        } else {
            hideRecyclerView()
        }
    }

    private fun hideRecyclerViewWithAnimation() {
        recyclerView.startAnimation(ScaleAnimation(1F, 1F, 1F, 0F).apply {
            duration = animationDuration
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation?) {
                    hideRecyclerView()
                }
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
            })
        })
    }

    private fun hideRecyclerView() {
        recyclerView.isVisible = false
        lineView.isVisible = showLine
    }

    private fun expandRecyclerView(showAnimation: Boolean = isAnimationEnabled()) {
        if (showAnimation) {
            showRecyclerViewWithAnimation()
        } else {
            showRecyclerView()
        }
    }

    private fun showRecyclerViewWithAnimation() {
        recyclerView.startAnimation(ScaleAnimation(1F, 1F, 0F, 1F).apply {
            duration = animationDuration
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    showRecyclerView()
                }
                override fun onAnimationEnd(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
            })
        })
    }

    private fun showRecyclerView() {
        recyclerView.isVisible = true
        lineView.isVisible = false
    }

    private fun isAnimationEnabled() = animationDuration != 0L

    override fun setOnClickListener(l: OnClickListener?) {
        tvTitle.setOnClickListener(l)
    }

}