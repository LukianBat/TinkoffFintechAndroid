package ru.tinkoff.demo.homework_one;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


public class App extends Application {

    private DataHolder holder;
    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
    public DataHolder getHolder() {
        return holder;
    }

    public void setHolder(DataHolder holder) {
        this.holder = holder;
    }
    public void cleanHolder(){
        this.holder=null;
    }

}