package com.iamsdt.firebasechatdemo.login

import am.appwise.components.ni.NoInternetDialog
import am.appwise.components.ni.NoInternetUtils
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.iamsdt.firebasechatdemo.BaseActivity
import com.iamsdt.firebasechatdemo.R
import kotlinx.android.synthetic.main.content_login.*
import javax.inject.Inject


class LoginActivity : BaseActivity() {

    @Inject lateinit var mAuth:FirebaseAuth
    @Inject lateinit var dialog:NoInternetDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        //inject
        getComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val authUtil = FirebaseAuthUtil(mAuth = mAuth)

        val state = NoInternetUtils.isConnectedToInternet(this@LoginActivity)

        if (!state) {
            showNoInternetDialog()
        }else{
            authUtil.checkUserData(this@LoginActivity)
        }

        login_btn.setOnClickListener {
            val email = login_email_lay.editText?.text.toString()
            val pass = login_pass_lay.editText?.text.toString()
            if (state){
                authUtil.login(this, email, pass)
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
        if (!dialog.isShowing){
            dialog.show()
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
