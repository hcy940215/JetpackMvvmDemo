package me.hgj.jetpackmvvm.demo.app.network

import me.hgj.jetpackmvvm.demo.app.util.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        CacheUtil.getUser()?.let {
            builder.addHeader("Authorization", it.token)
        }
        return chain.proceed(builder.build())
    }

}