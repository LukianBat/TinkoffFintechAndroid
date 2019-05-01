package com.memebattle.myapplication.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.memebattle.myapplication.R
import com.memebattle.myapplication.feature.add.AddNodeFragment
import com.memebattle.myapplication.feature.list.NodesListFragment
import com.memebattle.myapplication.feature.node.NodeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var manager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container, NodesListFragment())
        transaction.commit()
    }

    override fun onBackPressed() {
        val fragment = manager.findFragmentById(R.id.container)
        val transaction = manager.beginTransaction()
        if (fragment is AddNodeFragment) {
            transaction.remove(fragment).add(R.id.container, NodesListFragment()).commit()
        }
        if (fragment is NodeFragment) {
            transaction.remove(fragment).add(R.id.container, NodesListFragment()).commit()
        }
        if (fragment is NodesListFragment) {
            finish()
        }
    }
}
