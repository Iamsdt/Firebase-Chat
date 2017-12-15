package com.iamsdt.firebasechatdemo.adapter

import android.widget.AdapterView
import com.iamsdt.firebasechatdemo.model.Post

/**
 * Created by Shudipto Trafder on 12/15/2017.
 * at 1:37 PM
 */
interface ClickListener{
    fun onItemClick(post: Post?)
}