package com.iamsdt.firebasechatdemo.login

import am.appwise.components.ni.NoInternetDialog
import am.appwise.components.ni.NoInternetUtils
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.iamsdt.firebasechatdemo.R
import kotlinx.android.synthetic.main.signup_fragment.*

/**
 * Created by Shudipto Trafder on 12/12/2017.
 * at 11:25 PM
 */
class SignupFragment:Fragment(){


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.signup_fragment,container,false)

        val authUtil = FirebaseAuthUtil()

        val btn = view!!.findViewById<Button>(R.id.sUp_btn)

        btn.setOnClickListener {
            val email = sUp_email_lay.editText?.text.toString()
            val pass = sUp_pass.editText?.text.toString()
            //fixme 12/13/2017 validate before create
            if (NoInternetUtils.isConnectedToInternet(context)){
                authUtil.createNewAccount(context,email,pass)
            }else{
                NoInternetDialog.Builder(this).build().showDialog()
            }
        }

        return view
    }

}