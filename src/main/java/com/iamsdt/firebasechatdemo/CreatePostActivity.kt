package com.iamsdt.firebasechatdemo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.iamsdt.firebasechatdemo.model.Post
import com.iamsdt.firebasechatdemo.utility.ConstantUtils
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.content_create_post.*
import java.text.SimpleDateFormat
import java.util.*

class CreatePostActivity : AppCompatActivity() {

    private var dbRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        setSupportActionBar(toolbar)

        dbRef = FirebaseDatabase.getInstance().reference
//        val user = Fi
//
//        create_post_btn.setOnClickListener({
//            saveData(user)
//        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun saveData(user: FirebaseUser) {

        val text: String = create_postEt.text.toString()

        //data pattern May 9, 15:14pm
        val date = SimpleDateFormat("MMM dd, hh:mm a",
                Locale.ENGLISH).format(Date())

        val post = Post(content = text,date = date,userId = user.uid)

        dbRef?.child(user.uid)?.child(ConstantUtils.post)?.push()?.
                setValue(post)?.addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                finish()
            }
        })
    }

}
