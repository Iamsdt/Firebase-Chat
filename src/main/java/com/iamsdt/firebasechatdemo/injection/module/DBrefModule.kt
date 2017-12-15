package com.iamsdt.firebasechatdemo.injection.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Shudipto Trafder on 12/15/2017.
 * at 4:50 PM
 */
class DBrefModule(){

    fun getDBref(database:FirebaseDatabase):DatabaseReference
            = database.reference

    fun getDB():FirebaseDatabase = FirebaseDatabase.getInstance()
}