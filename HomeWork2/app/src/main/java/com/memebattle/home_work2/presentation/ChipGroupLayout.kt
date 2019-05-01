package com.memebattle.home_work2.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

open class ChipGroupLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    private var deviceWidth = context.resources.displayMetrics.widthPixels

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        var curWidth: Int
        var curHeight: Int
        var curRight: Int
        var curTop: Int
        var maxHeight: Int

        val childLeft = paddingLeft
        val childTop = paddingTop
        val childRight = measuredWidth - paddingRight
        val childBottom = measuredHeight - paddingBottom
        val childWidth = childRight - childLeft
        val childHeight = childBottom - childTop

        maxHeight = 0
        curRight = childRight
        curTop = childTop

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility == View.GONE)
                return

            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST))
            curWidth = child.measuredWidth
            curHeight = child.measuredHeight

            if (curRight - curWidth < childLeft) {
                curRight = childRight
                curTop += maxHeight
                maxHeight = 0
            }

            child.layout(curRight - curWidth, curTop, curRight, curTop + curHeight)
            if (maxHeight < curHeight)
                maxHeight = curHeight
            curRight -= curWidth
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount

        var maxHeight = 0
        var maxWidth = 0
        var childState = 0
        var leftWidth = 0
        var rowCount = 0



        for (i in 0 until count) {
            val child = getChildAt(i)

            if (child.visibility == View.GONE)
                continue


            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            maxWidth += Math.max(maxWidth, child.measuredWidth)
            leftWidth += child.measuredWidth

            if (leftWidth / deviceWidth > rowCount) {
                maxHeight += child.measuredHeight
                rowCount++
            } else {
                maxHeight = Math.max(maxHeight, child.measuredHeight)
            }
            childState = View.combineMeasuredStates(childState, child.measuredState)
        }


        maxHeight = Math.max(maxHeight, suggestedMinimumHeight)
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth)


        setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                View.resolveSizeAndState(maxHeight, heightMeasureSpec, childState shl View.MEASURED_HEIGHT_STATE_SHIFT))
    }
}