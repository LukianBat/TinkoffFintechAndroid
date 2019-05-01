package com.memebattle.myapplication.feature.node

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.memebattle.myapplication.feature.node.child.ChildFragment
import com.memebattle.myapplication.feature.node.parents.ParentsFragment
import com.memebattle.myapplication.R


class TiesPageAdapter(fm: FragmentManager, val id: Long, private val context: Context, var parentsFragment: ParentsFragment, var childFragment: ChildFragment) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                parentsFragment.id = this.id
                return parentsFragment
            }
            1 -> {
                childFragment.id = this.id
                return childFragment
            }
        }
        return null
    }


    override fun getPageTitle(position: Int): String {
        return if (position == 0) {
            context.resources.getString(R.string.parent)
        } else {
            context.resources.getString(R.string.child)
        }
    }
}