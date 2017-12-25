package com.iamsdt.firebasechatdemo.injection.module

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.iamsdt.firebasechatdemo.MainActivity
import com.iamsdt.firebasechatdemo.adapter.MainAdapter
import com.iamsdt.firebasechatdemo.injection.scopes.ApplicationScope
import com.iamsdt.firebasechatdemo.injection.scopes.MActivityScope
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:52 AM
 */

@Module(includes = [ContextModule::class, DBRefModule::class])
class MActivityModule{

    @Provides
    @ApplicationScope
    fun getAdapter(dbRef:DatabaseReference,picasso: Picasso,context:Context):MainAdapter
            = MainAdapter(dbRef,picasso,context)
}