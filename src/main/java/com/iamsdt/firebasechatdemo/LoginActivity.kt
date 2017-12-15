package com.iamsdt.firebasechatdemo

import am.appwise.components.ni.NoInternetDialog
import am.appwise.components.ni.NoInternetUtils
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iamsdt.firebasechatdemo.login.FirebaseAuthUtil
import com.iamsdt.firebasechatdemo.login.SignupFragment
import kotlinx.android.synthetic.main.content_login.*


class LoginActivity : AppCompatActivity() {

    var authUtil:FirebaseAuthUtil ?= null
    var dialog:NoInternetDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authUtil = FirebaseAuthUtil(MyApplication().get(this).mAuth!!)
        dialog = NoInternetDialog.Builder(this).build()

        val state = NoInternetUtils.isConnectedToInternet(this@LoginActivity)

        if (!state) {
            showNoInternetDialog()
        }else{
            authUtil?.checkUserData(this@LoginActivity)
        }

        login_btn.setOnClickListener {
            val email = login_email_lay.editText?.text.toString()
            val pass = login_pass_lay.editText?.text.toString()
            if (state){
                authUtil?.login(this, email, pass)
            }else{
                showNoInternetDialog()
            }
        }

        login_signup.setOnClickListener {
            val fragment = SignupFragment()
            val manager = supportFragmentManager.beginTransaction()
            manager.replace(R.id.login_container, fragment)
            manager.addToBackStack(null)
            if (state) {
                manager.commit()
            }else showNoInternetDialog()
        }

    }

    private fun showNoInternetDialog(){
        if (!dialog!!.isShowing){
            dialog?.show()
        }
    }

    override fun onStop() {
        super.onStop()
        finishCall()
    }

    private fun finishCall(){
        if (finishedRequest){
            finish()
        }
    }

    companion object {
        var finishedRequest = false
    }
}
