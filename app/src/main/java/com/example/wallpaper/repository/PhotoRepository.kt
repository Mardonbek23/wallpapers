package com.example.wallpaper.repository

import com.example.wallpaper.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class PhotoRepository(private var apiService: ApiService) {

    fun getPhotos(page: Int) =
        flow {
            emit(apiService.getRandomPhotos(page = page)) }

    fun getSearchPhotos(page: Int,query:String,order_by:String) = flow {
        emit(apiService.getSearchPhotos(page = page, query = query, order_by = order_by))
    }
}