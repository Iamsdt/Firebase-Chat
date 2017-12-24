package com.iamsdt.firebasechatdemo.injection.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.iamsdt.firebasechatdemo.injection.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/15/2017.
 * at 4:50 PM
 */

@Module
class DBRefModule{

    @Provides
    @ApplicationScope
    fun getDBref(database:FirebaseDatabase):DatabaseReference
            = database.reference

    @Provides
    @ApplicationScope
    fun getDB():FirebaseDatabase = FirebaseDatabase.getInstance()
}