package com.iamsdt.firebasechatdemo.model

/**
 * Created by Shudipto Trafder on 12/14/2017.
 * at 8:37 PM
 */
data class Post(
        //post
        var content: String = "",
        var date: String = "",
        var postMedia:String ="",

        //key -> push key
        var key: String = "",

        //user
        var userId:String = "",
        var userName:String = "Shudipto",
        var userProfilePic:String = "",

        //love and share
        var loveCount:Int = 0,
        var shareCount:Int =0
)