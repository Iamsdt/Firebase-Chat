package com.iamsdt.firebasechatdemo.injection

import android.app.Activity
import com.iamsdt.firebasechatdemo.injection.module.ActivityModule
import com.iamsdt.firebasechatdemo.injection.scopes.ActivityScope
import dagger.Component

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:55 AM
 */
@ActivityScope
@Component(modules = [ActivityModule::class],
        dependencies = [(ApplicationComponent::class)])
interface ActivityComponent{

    fun inject(activity: Activity)
}