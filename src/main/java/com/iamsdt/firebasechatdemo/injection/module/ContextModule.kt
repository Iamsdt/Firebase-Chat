package com.iamsdt.firebasechatdemo.injection.module

import android.content.Context
import com.iamsdt.firebasechatdemo.injection.scopes.AppilcationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:31 AM
 */
@Module
class ContextModule(context:Context){

    private val mContext = context

    @Provides
    @AppilcationScope
    fun getContext(): Context = mContext
}