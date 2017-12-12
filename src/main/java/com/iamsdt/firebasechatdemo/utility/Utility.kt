package com.iamsdt.firebasechatdemo.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.iamsdt.firebasechatdemo.BuildConfig


/**
 * Created by Shudipto Trafder on 12/12/2017.
 * at 10:29 PM
 */

class Utility{
    companion object {

        /**Show log message in the debug mode
         * @param message log message
         * @param tag log tag
         * @param throwable the error that throw*/
        fun logger(message:String,
                   tag:String = "custom",
                   throwable: Throwable? = null){

            if (BuildConfig.DEBUG){
                Log.e(tag,message,throwable)
            }
        }



        fun saveUserToSp(context: Context,string: String){
            val sp = context.getSharedPreferences("Firebase User",Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("UserID:",string)
            editor.apply()
        }

        fun getUserFromSp(context: Context):String{
            val sp = context.getSharedPreferences("Firebase User",Context.MODE_PRIVATE)
            return sp.getString("UserID:",null)
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val info: NetworkInfo = manager.activeNetworkInfo

            return info.isConnectedOrConnecting
        }
    }
}