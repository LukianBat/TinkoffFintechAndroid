package com.memebattle.home_work2.presentation

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import com.memebattle.home_work2.R
import kotlinx.android.synthetic.main.chip_item.view.*

class ChipLayout(context: Context?, chip: MyChip) : FrameLayout(context) {

    //не получилось наследоваться от ViewGroup, поменял на FrameLayout и убрал <merge>

    init {
        inflate(context, R.layout.chip_item, this)
        val intColor: Int = Color.parseColor(resources.getString(chip.color))
        marker.setBackgroundColor(intColor)
        marker.setBackgroundResource(R.drawable.ic_fiber_manual_record_black_24dp)
        close.setBackgroundColor(intColor)
        close.setBackgroundResource(R.drawable.ic_close_black_24dp)
        chipText.setTextColor(intColor)
        chipText.text = resources.getString(chip.chipText)
    }
}