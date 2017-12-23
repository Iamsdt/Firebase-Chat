package com.iamsdt.firebasechatdemo

import android.app.Activity
import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 12/14/2017.
 * at 7:11 PM
 */
class MyApplication:Application(){

    var mAuth:FirebaseAuth ?= null
    var dbRef: DatabaseReference? = null


    override fun onCreate() {
        super.onCreate()

        //planting timber
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }else{
            Timber.plant(Timber.asTree())
        }

        mAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference
    }

    fun get(activity: Activity):MyApplication = activity.application as MyApplication
}
