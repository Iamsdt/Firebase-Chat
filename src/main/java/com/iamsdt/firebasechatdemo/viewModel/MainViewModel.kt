package com.iamsdt.firebasechatdemo.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.iamsdt.firebasechatdemo.model.Post
import com.iamsdt.firebasechatdemo.utility.ConstantUtils
import timber.log.Timber

/**
 * Created by Shudipto Trafder on 12/15/2017.
 * at 4:21 PM
 */
class MainViewModel(application: Application):AndroidViewModel(application){

    private var postList: MutableLiveData<List<Post>>? = null

    private var dbRef: DatabaseReference? = null
    private var user: FirebaseUser? = null


    init {
        dbRef = FirebaseDatabase.getInstance().reference
        Timber.i(dbRef.toString())
    }

    fun getPostList(user: FirebaseUser?):MutableLiveData<List<Post>>?{

        if (postList == null){
            postList = MutableLiveData()

            if (user != null){
                getAllData(user)
            }
        }
        return postList
    }

    private fun getAllData(user: FirebaseUser) {

        AsyncTask.execute({

            postList = MutableLiveData()

            dbRef?.child(user.uid)?.child(ConstantUtils.post)?.
                    addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            //value changed
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot?) {

                            val array = ArrayList<Post>()

                            for (snapShot in dataSnapshot!!.children) {

                                if (!snapShot.exists()) {
                                    continue
                                }
                                val data = snapShot.getValue(Post::class.java)
                                val key = snapShot.key
                                Timber.i(key)
                                val post = Post(
                                        data!!.content,
                                        data.date,
                                        data.postMedia,
                                        key,
                                        data.userId,
                                        data.userName,
                                        data.userProfilePic,
                                        data.loveCount,
                                        data.shareCount)
                                //add new post data
                                array.add(post)
                                Timber.i(post.toString())
                            }

                            if (array.isNotEmpty()) {
                                postList?.postValue(array)
                            }

                        }
                    })
        })
    }
}
