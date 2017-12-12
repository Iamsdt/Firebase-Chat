package com.iamsdt.firebasechatdemo.utility

import android.util.Log
import com.iamsdt.firebasechatdemo.BuildConfig

/**
 * Created by Shudipto Trafder on 12/13/2017.
 * at 12:52 AM
 */

class Logger {
    companion object {

        fun error(message: String,
                  tag: String = "custom",
                  throwable: Throwable? = null,
                  info:Boolean = false,
                  verb:Boolean = false) {

            if (BuildConfig.DEBUG) {
                Log.e(tag, message, throwable)
            }

            if (info){
                infor(message,tag,throwable)
            }

            if (verb){
                verbose(message,tag,throwable)
            }
        }

        fun infor(message: String,
                 tag: String = "custom_information",
                 throwable: Throwable? = null) {

                Log.i(tag, message, throwable)

        }

        fun warn(message: String,
                 tag: String = "custom_warning",
                 throwable: Throwable? = null,
                 info:Boolean = false,
                 verb:Boolean = false) {

            if (BuildConfig.DEBUG) {
                Log.w(tag, message, throwable)
            }

            if (info){
                infor(message,tag,throwable)
            }

            if (verb){
                verbose(message,tag,throwable)
            }
        }

        fun verbose(message: String,
                    tag: String = "custom_verbose",
                    throwable: Throwable? = null) {

            Log.v(tag, message, throwable)

        }
    }
}