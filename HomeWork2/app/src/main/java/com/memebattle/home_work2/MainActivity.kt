package com.memebattle.home_work2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.memebattle.home_work2.presentation.ChipLayout
import com.memebattle.home_work2.presentation.MyChip
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val firstArrayListView = arrayListOf<MyChip>()
    private val secondArrayListView = arrayListOf<MyChip>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addInArrayList()
        chipGroupContainer.addViewsInFirstContainer(firstArrayListView)
        chipGroupContainer.addViewsInSecondContainer(secondArrayListView)

    }

    private fun addInArrayList() {
        firstArrayListView.add(MyChip(R.string.colorMaterialBlue, R.string.java))
        firstArrayListView.add(MyChip(R.string.colorMaterialBlue, R.string.java))
        firstArrayListView.add(MyChip(R.string.colorMaterialGreen, R.string.kotlin))
        firstArrayListView.add(MyChip(R.string.colorMaterialRed, R.string.dagger))
        firstArrayListView.add(MyChip(R.string.colorMaterialOrange, R.string.moxy))
        firstArrayListView.add(MyChip(R.string.colorPrimary, R.string.mvvm))
        firstArrayListView.add(MyChip(R.string.colorMaterialRed, R.string.room))
        secondArrayListView.add(MyChip(R.string.colorMaterialGreen, R.string.retrofit))
        secondArrayListView.add(MyChip(R.string.colorMaterialOrange, R.string.rx))
        secondArrayListView.add(MyChip(R.string.colorAccent, R.string.clean_architecture))
        secondArrayListView.add(MyChip(R.string.colorMaterialGreen, R.string.async_task))
        secondArrayListView.add(MyChip(R.string.colorMaterialRed, R.string.nav_graph))
        secondArrayListView.add(MyChip(R.string.colorMaterialGreen, R.string.xml))
    }

}
