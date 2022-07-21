package com.example.wallpaper.retrofit

import com.example.a4kw.models.PhotoData
import com.example.wallpaper.models.s.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("photos")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int?,
        @Query("order_by") order_by: String,
    ): Call<List<PhotoData>>

    @GET("photos")
    fun getnewPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int?,
        @Query("order_by") order_by: String,
        @Query("client_id") client_id: String,
    ): Call<List<PhotoData>>

    @GET("photos/random")
   suspend fun getRandomPhotos(
        @Query("page") page: Int=1,
        @Query("client_id") client_id: String="iXPc9MjBD9MkKg1WVBTSNWfEE8fMoX2t64OqM3ZhN48",
        @Query("count") count: Int=30,
    ): List<PhotoData>

    @GET("search/photos")
    suspend fun getSearchPhotos(
        @Query("page") page: Int=1,
        @Query("per_page") per_page: Int? = 30,
        @Query("query") query: String?="office",
        @Query("order_by") order_by: String?="relevant",
        @Query("client_id")id:String="iXPc9MjBD9MkKg1WVBTSNWfEE8fMoX2t64OqM3ZhN48"
    ): Search
}