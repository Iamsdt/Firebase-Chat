package com.iamsdt.firebasechatdemo.login

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.iamsdt.firebasechatdemo.MainActivity
import com.iamsdt.firebasechatdemo.utility.Logger

/**
 * Created by Shudipto Trafder on 12/13/2017.
 * at 12:32 AM
 */
class FirebaseAuthUtil{

    private var mAuth:FirebaseAuth ?= null

    private val userKey = "FirebaseUser"
    private val userEmailKey = "email"
    private val userPassKey = "pass"

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    fun checkUserData(context: Context):Boolean{

        val user = getUserEmilAndPass(context)

        val email = user.getValue(userEmailKey)
        val pass = user.getValue(userPassKey)

        if (email.isEmpty() || pass.isEmpty()){
            return false
        }

        return true
    }

    private fun getUserEmilAndPass(context: Context):HashMap<String,String>{
        val sp = context.getSharedPreferences(userKey, Context.MODE_PRIVATE)
        val email = sp.getString(userEmailKey,null)
        //fixme 12/13/2017 decrypt pass before use
        val pass = sp.getString(userEmailKey,null)
        val hasMap = HashMap<String,String>()
        hasMap["email"] = email
        hasMap["pass"] = pass
        return hasMap
    }

    fun login(context: Context, email: String?, pass:String?){

        var userEmail = email
        var userPass = pass


        if (userEmail.isNullOrEmpty()||userPass.isNullOrEmpty()){
            val user = getUserEmilAndPass(context)
            userEmail = user.getValue(userEmailKey)
            userPass = user.getValue(userPassKey)
        }

        mAuth?.signInWithEmailAndPassword(userEmail!!,userPass!!)
                ?.addOnCompleteListener({
                    task->
                    if (task.isSuccessful){
                        context.startActivity(Intent(context,MainActivity::class.java))
                    }
                })
    }

    fun createNewAccount(context: Context, email: String, pass:String){

        mAuth?.createUserWithEmailAndPassword(email,pass)
                ?.addOnCompleteListener({
                    task ->
                    if (task.isSuccessful){
                        val user = mAuth?.currentUser!!
                        saveUserToSp(context,user.email!!,pass)
                    } else{
                        Logger.error("User sign up failed",
                                "user signup",task.exception,true)
                    }
                })
    }

    private fun saveUserToSp(context: Context, email: String, pass:String){
        val sp = context.getSharedPreferences(userKey, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(userEmailKey,email)
        //fixme 12/13/2017 encrypt the password before save
        editor.putString(userPassKey,pass)
        editor.apply()
    }
}