package com.iamsdt.firebasechatdemo.injection.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.iamsdt.firebasechatdemo.injection.scopes.AppilcationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/15/2017.
 * at 4:50 PM
 */

@Module
class DBRefModule{

    @Provides
    @AppilcationScope
    fun getDBref(database:FirebaseDatabase):DatabaseReference
            = database.reference

    @Provides
    @AppilcationScope
    fun getDB():FirebaseDatabase = FirebaseDatabase.getInstance()
}