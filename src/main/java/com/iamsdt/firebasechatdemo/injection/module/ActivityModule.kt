package com.iamsdt.firebasechatdemo.injection.module

import am.appwise.components.ni.NoInternetDialog
import com.google.firebase.database.DatabaseReference
import com.iamsdt.firebasechatdemo.BaseActivity
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
class ActivityModule(private val activity:BaseActivity){

    @Provides
    @ActivityScope
    fun getAdapter(dbRef:DatabaseReference,picasso: Picasso):MainAdapter
            = MainAdapter(dbRef,picasso,activity)

    @Provides
    @ActivityScope
    fun getNoInternetDialog():NoInternetDialog
            = NoInternetDialog.Builder(activity.baseContext).build()
}