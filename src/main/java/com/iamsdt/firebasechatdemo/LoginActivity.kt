package com.iamsdt.firebasechatdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.iamsdt.firebasechatdemo.login.FirebaseAuthUtil
import com.iamsdt.firebasechatdemo.login.SignupFragment
import com.iamsdt.firebasechatdemo.utility.Utility
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        if (!Utility.isNetworkAvailable(this)){
            //add a layout with no internet
        }

        val authUtil = FirebaseAuthUtil()

        val status = authUtil.checkUserData(this@LoginActivity)

        if (status){
            authUtil.login(this,null,null)
        }

        login_btn.setOnClickListener {
            val email = login_email_lay.editText?.text.toString()
            val pass = login_pass_lay.editText?.text.toString()
            authUtil.login(this,email,pass)
        }

        login_signup.setOnClickListener {
            val fragment = SignupFragment()
            val manager = supportFragmentManager.beginTransaction()
            manager.replace(R.id.login_container,fragment)
            manager.addToBackStack(null)
            manager.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
