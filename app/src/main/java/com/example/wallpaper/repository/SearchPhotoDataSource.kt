package com.example.wallpaper.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaper.models.s.Result
import com.example.wallpaper.retrofit.ApiClient
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.io.IOException

class SearchPhotoDataSource(var query:String,var order_by:String):PagingSource<Int,Result>() {
    private var photoRepository = PhotoRepository(ApiClient.apiService())
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        try {
            val currentPage = params.key ?: 1
            var prevKey: Int? = currentPage - 1
            if (currentPage == 1) {
                prevKey = null
            }
            Log.d("key", "load: " + currentPage)
            var loadResult: LoadResult.Page<Int, Result>? = null

            if (params.key ?: 1 >= currentPage) {
                photoRepository.getSearchPhotos(currentPage, query = query,order_by=order_by)
                    .catch {
                        Log.d("catch", "load: " + currentPage)
                        loadResult = LoadResult.Page(emptyList(), prevKey, currentPage + 1)
                    }
                    .collect {
                        Log.d("collect", "load2: " + currentPage)
                        loadResult = LoadResult.Page(it.results, prevKey, currentPage + 1)
                    }
            } else {
                loadResult = LoadResult.Page(emptyList(), prevKey, currentPage + 1)
            }
            return loadResult!!
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
        catch (e:HttpException){
            return LoadResult.Error(e)
        }

    }
}