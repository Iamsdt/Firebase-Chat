package com.iamsdt.firebasechatdemo.injection.module

import android.content.Context
import com.iamsdt.firebasechatdemo.injection.scopes.AppilcationScope
import com.squareup.okhttp.Cache
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.Downloader
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Shudipto Trafder on 12/25/2017.
 * at 12:24 AM
 */

@Module(includes = arrayOf(ContextModule::class))
class PicassoModule{

    @Provides
    @AppilcationScope
    fun getPicasso(context: Context,downloader: Downloader):Picasso = Picasso.Builder(context)
            .downloader(downloader)
            .build()

    @Provides
    @AppilcationScope
    fun getDownloader(client:OkHttpClient):Downloader = OkHttpDownloader(client)


    @Provides
    @AppilcationScope
    fun getClient(cache:Cache):OkHttpClient{
        val client = OkHttpClient()
        client.cache = cache
        client.setConnectTimeout(1, TimeUnit.MINUTES)
        client.setReadTimeout(1, TimeUnit.MINUTES)
        return client
    }

    @Provides
    @AppilcationScope
    fun getCache(file: File): Cache = Cache(file, 10 * 1024 * 1024)

    @Provides
    @AppilcationScope
    fun getFile(context: Context): File = File(context.cacheDir, "okHttp")
}