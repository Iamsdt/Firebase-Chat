package com.iamsdt.firebasechatdemo

import am.appwise.components.ni.NoInternetDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iamsdt.firebasechatdemo.adapter.MainAdapter
import com.iamsdt.firebasechatdemo.injection.DaggerActivityComponent
import com.iamsdt.firebasechatdemo.injection.module.ActivityModule
import com.iamsdt.firebasechatdemo.login.FirebaseAuthUtil
import com.iamsdt.firebasechatdemo.login.LoginActivity
import com.iamsdt.firebasechatdemo.messenger.MessengerActivity
import com.iamsdt.firebasechatdemo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(){

    @Inject lateinit  var mAuth:FirebaseAuth
    @Inject lateinit  var user:FirebaseUser

    @Inject lateinit var mAdapter: MainAdapter

    @Inject lateinit var dialog: NoInternetDialog

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //component
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        val manager = LinearLayoutManager(this)
        mainRcv.layoutManager = manager
        mainRcv.adapter = mAdapter

        viewModel.getPostList(user)?.observe(this, Observer { allData ->
            if (allData != null && allData.isNotEmpty()) {
                mAdapter.swapData(allData)
                Timber.w(allData.size.toString())
            }
        })

        main_card.setOnClickListener({
            startActivity(Intent(this, CreatePostActivity::class.java))
        })

        main_fab.setOnClickListener({
            startActivity(Intent(this, CreatePostActivity::class.java))
        })

        //hide fab on scroll
        //fab hide with recycler view scroll
        mainRcv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 1)
                    main_fab.hide()
                else if (dy < 1)
                    main_fab.show()
            }
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

            //todo 12/24/2017 move to settings
            R.id.action_logOut -> {
                mAuth.signOut()
                FirebaseAuthUtil(mAuth).removeUserFromSp(this)
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                return true
            }

            R.id.action_message -> {
                startActivity(Intent(this@MainActivity, MessengerActivity::class.java))
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
