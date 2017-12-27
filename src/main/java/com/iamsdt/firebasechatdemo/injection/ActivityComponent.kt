package com.iamsdt.firebasechatdemo.injection

import android.app.Activity
import com.iamsdt.firebasechatdemo.CreatePostActivity
import com.iamsdt.firebasechatdemo.MainActivity
import com.iamsdt.firebasechatdemo.injection.module.ActivityModule
import com.iamsdt.firebasechatdemo.injection.scopes.ActivityScope
import com.iamsdt.firebasechatdemo.login.LoginActivity
import com.iamsdt.firebasechatdemo.messenger.ChatActivity
import com.iamsdt.firebasechatdemo.messenger.MessengerActivity
import com.iamsdt.firebasechatdemo.profile.FriendSearchActivity
import com.iamsdt.firebasechatdemo.profile.ProfileActivity
import dagger.Component

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:55 AM
 */
@ActivityScope
@Component(modules = [ActivityModule::class],
        dependencies = [(ApplicationComponent::class)])
interface ActivityComponent{

    fun inject(mainActivity: MainActivity)

    fun inject(loginActivity: LoginActivity)
    fun inject(createPostActivity: CreatePostActivity)

    //messenger
    fun inject(chatActivity: ChatActivity)
    fun inject(messengerActivity: MessengerActivity)


    //profile
    fun inject(friendSearchActivity: FriendSearchActivity)
    fun inject(profileActivity: ProfileActivity)
}