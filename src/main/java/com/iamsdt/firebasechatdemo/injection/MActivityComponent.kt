package com.iamsdt.firebasechatdemo.injection

import com.iamsdt.firebasechatdemo.MainActivity
import com.iamsdt.firebasechatdemo.injection.module.MActivityModule
import com.iamsdt.firebasechatdemo.injection.scopes.MActivityScope
import dagger.Component

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:55 AM
 */

@MActivityScope
@Component(modules = arrayOf(MActivityModule::class),
        dependencies = arrayOf(ApplicationComponent::class))
interface MActivityComponent {
    fun mainActivity(mainActivity: MainActivity)
}