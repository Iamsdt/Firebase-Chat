package com.iamsdt.firebasechatdemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.iamsdt.firebasechatdemo.R
import com.iamsdt.firebasechatdemo.model.Post
import com.iamsdt.firebasechatdemo.utility.ConstantUtils
import kotlinx.android.synthetic.main.main_list_item.view.*
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 12/14/2017.
 * at 7:48 PM
 */
class MainAdapter(private val databaseReference: DatabaseReference):
        RecyclerView.Adapter<MainAdapter.MyViewHolder>(){

    private var dataList: List<Post>? = null

    override fun onBindViewHolder(viewHolder: MyViewHolder?, position: Int) {
        val post = dataList!![position]

        //profile
        viewHolder?.profileName?.text = post.userName
        viewHolder?.postDate?.text = post.date

        //post
        viewHolder?.postText?.text = post.content
        viewHolder?.shareCount?.text = post.shareCount.toString()
        viewHolder?.loveCount?.text = post.loveCount.toString()

        if (post.postMedia.isEmpty()){
            viewHolder?.postImage?.visibility = View.GONE
        }


        //code for love btn and share btn
        if (post.userId.isNotEmpty()){

            var loveCount = post.loveCount
            var shareCount = post.shareCount

            viewHolder?.loveBtn?.setOnClickListener({
               loveCount += 1

                val newPost = Post(post.content,post.date,post.postMedia,post.key,
                        post.userId,post.userName,post.userProfilePic,loveCount,post.shareCount)

                databaseReference.child(post.userId)?.child(ConstantUtils.post)
                        ?.child(post.key)?.setValue(newPost)
                        ?.addOnCompleteListener({ task ->
                            if (task.isSuccessful) {
                                Timber.i("update $loveCount")
                            } else {
                                Timber.e(task.exception)
                            }
                        })
            })

            viewHolder?.shareBtn?.setOnClickListener({

                shareCount += 1

                val newPost = Post(post.content,post.date,post.postMedia,post.key,
                        post.userId,post.userName,post.userProfilePic,post.loveCount,shareCount)

                databaseReference.child(post.userId)?.child(ConstantUtils.post)
                        ?.child(post.key)?.setValue(newPost)
                        ?.addOnCompleteListener({ task ->
                            if (task.isSuccessful) {
                                Timber.i("update + $shareCount")
                            } else {
                                Timber.e(task.exception)
                            }
                        })
            })
        }

        Timber.i(post.content)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, ViewType: Int):
            MyViewHolder {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.main_list_item, parent, false)
        return MyViewHolder(view)
    }

    fun swapData(list: List<Post>) {

        dataList = list

        if (dataList != null){
            //notify adapter about data is changed
            notifyDataSetChanged()
            Timber.i("array list changed")
        }

    }

    inner class MyViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView){

        val pofilePic:ImageView = itemView.main_profilePic
        val profileName:TextView = itemView.main_profileName
        val postDate:TextView = itemView.main_postDate

        //post
        val postImage:ImageView = itemView.main_post_img
        val postText:TextView = itemView.main_post_text

        //count
        val loveCount:TextView = itemView.main_post_love_count
        val shareCount:TextView = itemView.main_post_share_count

        //btn
        val loveBtn:TextView = itemView.main_post_love_btn
        val shareBtn:TextView = itemView.main_post_share_btn
    }
}