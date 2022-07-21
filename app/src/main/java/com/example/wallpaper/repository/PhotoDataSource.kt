package com.example.wallpaper.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.a4kw.models.PhotoData
import com.example.wallpaper.retrofit.ApiClient
import kotlinx.coroutines.flow.catch
import java.lang.Exception

class PhotoDataSource : PagingSource<Int, PhotoData>() {

    private var photoRepository = PhotoRepository(ApiClient.apiService())

    override fun getRefreshKey(state: PagingState<Int, PhotoData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoData> {
        try {
            val currentPage = params.key ?: 1
            var prevKey: Int? = currentPage - 1
            if (currentPage == 1) {
                prevKey = null
            }
            Log.d("key", "load: " + currentPage)
            var loadResult: LoadResult.Page<Int, PhotoData>? = null

            if (params.key ?: 1 >= currentPage) {
                photoRepository.getPhotos(currentPage)
                    .catch {
                        Log.d("catch", "load: " + currentPage)
                        loadResult = LoadResult.Page(emptyList(), prevKey, currentPage + 1)
                    }
                    .collect {
                        Log.d("collect", "load2: " + currentPage)
                        loadResult = LoadResult.Page(it, prevKey, currentPage + 1)
                    }
            } else {
                loadResult = LoadResult.Page(emptyList(), prevKey, currentPage + 1)
            }
            return loadResult!!
        } catch (e: Exception) {
            return LoadResult.Error(e.fillInStackTrace())
        }

    }
}