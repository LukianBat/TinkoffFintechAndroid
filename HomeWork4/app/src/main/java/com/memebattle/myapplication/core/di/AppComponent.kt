package com.memebattle.myapplication.core.di

import com.memebattle.myapplication.core.di.module.AppModule
import com.memebattle.myapplication.core.di.module.RoomModule
import com.memebattle.myapplication.feature.add.AddViewModule
import com.memebattle.myapplication.feature.list.ListViewModel
import com.memebattle.myapplication.feature.node.child.ChildViewModel
import com.memebattle.myapplication.feature.node.parents.ParentsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RoomModule::class])
interface AppComponent {
    fun inject(listViewModel: ListViewModel)
    fun inject(addViewModule: AddViewModule)
    fun inject(parentsViewModel: ParentsViewModel)
    fun inject(childViewModel: ChildViewModel)
}