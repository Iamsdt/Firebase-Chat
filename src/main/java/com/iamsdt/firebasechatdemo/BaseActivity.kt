package com.iamsdt.firebasechatdemo

import android.annotation.SuppressLint
import android.support.annotation.UiThread
import android.support.v7.app.AppCompatActivity
import com.iamsdt.firebasechatdemo.injection.ActivityComponent
import com.iamsdt.firebasechatdemo.injection.DaggerActivityComponent
import com.iamsdt.firebasechatdemo.injection.module.ActivityModule

/**
 * Created by Shudipto Trafder on 12/28/2017.
 * at 12:37 AM
 */
@SuppressLint("Registered")
open class BaseActivity :AppCompatActivity(){

    @UiThread
    fun getComponent(): ActivityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent(MyApplication().get(this).getComponent())
            .build()

}