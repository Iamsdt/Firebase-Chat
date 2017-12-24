package com.iamsdt.firebasechatdemo.injection.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iamsdt.firebasechatdemo.injection.scopes.AppilcationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:35 AM
 */
@Module
class AuthModule{

    @Provides
    @AppilcationScope
    fun getUser(mAuth:FirebaseAuth):FirebaseUser = mAuth.currentUser!!

    @Provides
    @AppilcationScope
    fun getAuth():FirebaseAuth = FirebaseAuth.getInstance()
}