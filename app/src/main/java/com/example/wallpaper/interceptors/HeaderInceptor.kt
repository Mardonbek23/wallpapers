package com.example.wallpaper.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInceptor(var clientId: String?):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request=chain.request()
        request=request.newBuilder()
            .addHeader("Authorization", "client_id=$clientId")
            .build()
        return chain.proceed(request)
    }
}