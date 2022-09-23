package kr.ac.kpu.ce2017154024.mytamin

import android.app.Application

class MyApplication : Application() {

    companion object{
        lateinit var instance:MyApplication
            private set
    }

    override fun onCreate() {
        instance=this
        super.onCreate()
    }


}