package com.iamsdt.firebasechatdemo

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.iamsdt.firebasechatdemo.adapter.ClickListener
import com.iamsdt.firebasechatdemo.adapter.MainAdapter
import com.iamsdt.firebasechatdemo.model.Post
import com.iamsdt.firebasechatdemo.utility.ConstantUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        ClickListener {
    private var database: FirebaseDatabase? = null

    private var dbRef: DatabaseReference? = null
    private var mAdapter: MainAdapter? = null

    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        database = FirebaseDatabase.getInstance()
        dbRef = database?.reference

        val manager = LinearLayoutManager(this)

        mainRcv.layoutManager = manager

        mAdapter = MainAdapter(this)

        mainRcv.adapter = mAdapter
        user = MyApplication().get(this).mAuth?.currentUser!!

        getData(user!!)

        main_btn.setOnClickListener({
            saveData(user!!)
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


    private fun saveData(user: FirebaseUser) {
        val text: String = mainEt.text.toString()
        val date = Date()

        //data pattern May 9, 15:14pm

        val post = Post(text,
                SimpleDateFormat("MMM dd, hh:mm a", Locale.ENGLISH).format(date))
        //data

        dbRef?.child(user.uid)?.child(ConstantUtils.post)?.push()
                ?.setValue(post)?.addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                mainEt.setText("")
                mainEt.clearFocus()
            }
        })
    }

    private fun getData(user: FirebaseUser) {

        val array = ArrayList<Post>()

        dbRef?.child(user.uid)?.child(ConstantUtils.post)?.
                addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                        //nothing to do
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot?) {

                        Timber.i("Data change called")

                        for (snapShot in dataSnapshot!!.children) {
                            val post = snapShot.getValue(Post::class.java)
                            if (!array.contains(post)) {
                                array.add(post!!)
                                Timber.i(post.toString())
                            }
                        }


                    }

                })

        mAdapter?.swapData(array)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onItemClick(post: Post?) {
        val newPost = Post("New Content", post!!.date)
        val map = mapOf<String, Any>(Pair("", newPost))
    }
}
