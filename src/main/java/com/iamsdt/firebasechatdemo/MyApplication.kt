package com.iamsdt.firebasechatdemo

import android.app.Activity
import android.app.Application
import com.iamsdt.firebasechatdemo.injection.ApplicationComponent
import com.iamsdt.firebasechatdemo.injection.DaggerApplicationComponent
import com.iamsdt.firebasechatdemo.injection.module.ContextModule
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 12/14/2017.
 * at 7:11 PM
 */
class MyApplication:Application(){


    var dagger:ApplicationComponent ?= null


    override fun onCreate() {
        super.onCreate()

        //planting timber
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }else{
            Timber.plant(Timber.asTree())
        }


        dagger = DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this))
                .build()

    }

    fun getComponent() = dagger

    fun get(activity: Activity):MyApplication = activity.application as MyApplication
}
