package com.memebattle.home_work2.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.chip_group_item.view.*

import com.memebattle.home_work2.R


class ChipGroupContainerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    //не получилось наследоваться от ViewGroup, поменял на FrameLayout и убрал <merge>

    init {
        inflate(context, R.layout.chip_group_item, this)
    }

    fun addViewInFirstContainer(myChip: MyChip) {
        val chipLayout = ChipLayout(context, myChip)
        firstContainer.addView(chipLayout)
        attachListener(chipLayout)
    }


    fun addViewsInFirstContainer(arrayList: ArrayList<MyChip>) {
        for (i in 0 until arrayList.size) {
            val chipLayout = ChipLayout(context, arrayList[i])
            firstContainer.addView(chipLayout)
            attachListener(chipLayout)
        }
    }

    fun addViewInSecondContainer(myChip: MyChip) {
        val chipLayout = ChipLayout(context, myChip)
        secondContainer.addView(chipLayout)
        attachListener(chipLayout)
    }

    fun addViewsInSecondContainer(arrayList: ArrayList<MyChip>) {
        for (i in 0 until arrayList.size) {
            val chipLayout = ChipLayout(context, arrayList[i])
            secondContainer.addView(chipLayout)
            attachListener(chipLayout)
        }
    }

    private fun attachListener(view: View) {
        view.setOnClickListener {
            val parent = view.parent as ChipGroupLayout
            parent.removeView(view)
            if (parent == secondContainer) {
                firstContainer.addView(view)
            }
            if (parent == firstContainer) {
                secondContainer.addView(view)
            }

        }

    }
}