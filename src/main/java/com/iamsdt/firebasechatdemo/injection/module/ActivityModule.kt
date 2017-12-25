package com.iamsdt.firebasechatdemo.injection.module

import android.app.Activity
import com.google.firebase.database.DatabaseReference
import com.iamsdt.firebasechatdemo.adapter.MainAdapter
import com.iamsdt.firebasechatdemo.injection.scopes.ActivityScope
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:52 AM
 */

@Module
class ActivityModule(private val activity:Activity){

    @Provides
    @ActivityScope
    fun getAdapter(dbRef:DatabaseReference,picasso: Picasso):MainAdapter
            = MainAdapter(dbRef,picasso,activity)
}