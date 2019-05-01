package com.memebattle.myapplication.feature.node


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memebattle.myapplication.R
import com.memebattle.myapplication.feature.node.child.ChildFragment
import com.memebattle.myapplication.feature.node.parents.ParentsFragment
import kotlinx.android.synthetic.main.fragment_node.*


class NodeFragment : Fragment() {

    var id: Long = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_node, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parentsFragment = ParentsFragment()
        val childFragment = ChildFragment()
        viewPager.adapter = TiesPageAdapter(activity!!.supportFragmentManager, id, context!!, parentsFragment, childFragment)
        tabLayout.setupWithViewPager(viewPager)
    }

}