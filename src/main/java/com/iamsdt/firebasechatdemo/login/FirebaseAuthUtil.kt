@file:Suppress("DEPRECATION")

package com.iamsdt.firebasechatdemo.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.iamsdt.firebasechatdemo.BuildConfig
import com.iamsdt.firebasechatdemo.MainActivity
import com.mobapphome.mahencryptorlib.MAHEncryptor
import timber.log.Timber
/**
 * Created by Shudipto Trafder on 12/13/2017.
 * at 12:32 AM
 */

class FirebaseAuthUtil(private val mAuth:FirebaseAuth){

    private val userKey = "FirebaseUser"
    private val userEmailKey = "email"
    private val userPassKey = "pass"

    private var mabEncrypt:MAHEncryptor ?= null

    private var dialog:ProgressDialog ?= null

    init {
        mabEncrypt = MAHEncryptor.newInstanceOrRetunNull(BuildConfig.PsswordApiKey)
    }

    fun checkUserData(context: Context){

        val user = getUserEmilAndPass(context)

        val email = user.getValue(userEmailKey)
        var pass = user.getValue(userPassKey)

        if (email.isNullOrEmpty() || pass.isNullOrEmpty()){
            Timber.i("Email or pass empty")
            return
        }

        //debug only 12/13/2017 remove later
        Timber.i("Email:$email pass:$pass")

        pass = mabEncrypt?.decode(pass)

        login(context,email!!,pass!!)
    }

    private fun getUserEmilAndPass(context: Context):HashMap<String?,String?>{
        val sp = context.getSharedPreferences(userKey, Context.MODE_PRIVATE)
        val email = sp.getString(userEmailKey,null)
        val pass = sp.getString(userPassKey,null)
        val hasMap = HashMap<String?,String?>()
        hasMap["email"] = email
        hasMap["pass"] = pass
        return hasMap
    }

    fun login(context: Context, email: String, pass:String){

        dialog = ProgressDialog(context)
        dialog?.setMessage("Login...")

        dialog?.show()

        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener({
                    task->
                    if (task.isSuccessful){
                        saveUserToSp(context,email,pass)
                        context.startActivity(Intent(context,MainActivity::class.java))

                        if (dialog!!.isShowing){
                            dialog?.dismiss()
                        }
                        LoginActivity.finishedRequest = true
                    } else{
                        Timber.plant(Timber.DebugTree())
                        Timber.e(task.exception,"Sign in failed")
                    }
                })
    }

    fun createNewAccount(context: Context, email: String, pass:String){

        dialog = ProgressDialog(context)
        dialog?.setMessage("Creating new account...")
        dialog?.show()

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener({
                    task ->
                    if (task.isSuccessful){
                        if (dialog!!.isShowing){
                            dialog?.dismiss()
                        }
                        val user = mAuth.currentUser!!
                        saveUserToSp(context,user.email!!,pass)
                        context.startActivity(Intent(context,MainActivity::class.java))
                        LoginActivity.finishedRequest = true
                    } else{
                        Timber.plant(Timber.DebugTree())
                        Timber.e(task.exception,"Sign in failed")
                    }
                })
    }

    private fun saveUserToSp(context: Context, email: String, pass:String){
        val sp = context.getSharedPreferences(userKey, Context.MODE_PRIVATE)
        val editor = sp.edit()

        val newPass = mabEncrypt?.encode(pass)

        editor.putString(userEmailKey,email)
        //complete 12/13/2017 encrypt the password before save
        Timber.i(newPass)
        editor.putString(userPassKey,newPass)
        editor.apply()
    }

    fun removeUserFromSp(context: Context){
        val sp = context.getSharedPreferences(userKey, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(userPassKey)
        editor.remove(userEmailKey)
        editor.apply()
    }
}