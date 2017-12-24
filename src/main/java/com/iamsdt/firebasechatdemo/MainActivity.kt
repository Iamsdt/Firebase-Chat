package com.iamsdt.firebasechatdemo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.iamsdt.firebasechatdemo.adapter.MainAdapter
import com.iamsdt.firebasechatdemo.login.FirebaseAuthUtil
import com.iamsdt.firebasechatdemo.login.LoginActivity
import com.iamsdt.firebasechatdemo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(){

    private var mAdapter: MainAdapter? = null

    private var dbRef: DatabaseReference? = null
    private var mAuth:FirebaseAuth ?= null

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        dbRef = MyApplication().get(this).dbRef

        mAuth = MyApplication().get(this).mAuth

        val build = UserProfileChangeRequest.Builder()
                .setDisplayName("Name")
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build()

        val manager = LinearLayoutManager(this)
        mainRcv.layoutManager = manager
        mAdapter = MainAdapter(dbRef!!)
        mainRcv.adapter = mAdapter


        viewModel.getPostList(mAuth?.currentUser)?.observe(this, Observer { allData ->
            if (allData != null && allData.isNotEmpty()) {
                mAdapter?.swapData(allData)
                Timber.w(allData.size.toString())
            }
        })

        main_card.setOnClickListener({
            startActivity(Intent(this, CreatePostActivity::class.java))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true

            R.id.action_logOut -> {
                mAuth?.signOut()
                FirebaseAuthUtil(mAuth!!).removeUserFromSp(this)
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
