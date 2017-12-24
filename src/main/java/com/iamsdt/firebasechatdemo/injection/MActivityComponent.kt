package com.iamsdt.firebasechatdemo.injection

import com.iamsdt.firebasechatdemo.MainActivity
import com.iamsdt.firebasechatdemo.injection.module.MainActivityModule
import com.iamsdt.firebasechatdemo.injection.scopes.ApplicationScope
import dagger.Component

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:55 AM
 */

@ApplicationScope
@Component(modules = arrayOf(MainActivityModule::class),
        dependencies = arrayOf(ApplicationComponent::class))
interface MActivityComponent {

    fun mainActivity(mainActivity: MainActivity)
}