package com.iamsdt.firebasechatdemo.injection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.iamsdt.firebasechatdemo.injection.module.AuthModule
import com.iamsdt.firebasechatdemo.injection.module.DBRefModule
import com.iamsdt.firebasechatdemo.injection.module.PicassoModule
import com.iamsdt.firebasechatdemo.injection.scopes.ApplicationScope
import com.squareup.picasso.Picasso
import dagger.Component

/**
 * Created by Shudipto Trafder on 12/15/2017.
 * at 4:48 PM
 */
@ApplicationScope
@Component(modules = arrayOf(AuthModule::class,DBRefModule::class,
        PicassoModule::class))
interface ApplicationComponent {
    val getPicasso: Picasso

    val getAuth:FirebaseAuth
    val getUser:FirebaseUser

    val getDbRef:DatabaseReference
}
