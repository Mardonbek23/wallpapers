package com.example.wallpaper.retrofit

import android.content.Context
import android.util.Config
import com.example.wallpaper.interceptors.HeaderInceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val Base_url="https://api.unsplash.com/"
    const val client_Id:String="se7GoO6TlLv6K27P2h_0nEGn5OU7Q6HaeqY-Jkn_UfI"
    fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(HeaderInceptor(client_Id))
            .build()

        return Retrofit.Builder()
            .baseUrl(Base_url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun apiService() = getRetrofit().create(ApiService::class.java)
}