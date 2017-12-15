package com.iamsdt.firebasechatdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.iamsdt.firebasechatdemo.R
import com.iamsdt.firebasechatdemo.model.Post
import kotlinx.android.synthetic.main.dummy_item_view.view.*
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 12/14/2017.
 * at 7:48 PM
 */
class MainAdapter:RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var dataList: ArrayList<Post>? = null

    init {
        dataList = ArrayList()
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder?, position: Int) {
        val post = dataList!![position]
        viewHolder?.textView?.text = post.content
        Timber.i(post.content)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, ViewType: Int):
            MyViewHolder {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.dummy_item_view, parent, false)
        return MyViewHolder(view)
    }

    fun swapData(list: ArrayList<Post>) {
        dataList = list

        if (dataList != null) {
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        val textView:TextView = itemView.dummyItemTv
    }
}