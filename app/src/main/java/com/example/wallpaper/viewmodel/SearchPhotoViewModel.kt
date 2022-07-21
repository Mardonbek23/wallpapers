package com.example.wallpaper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.wallpaper.repository.PhotoDataSource
import com.example.wallpaper.repository.SearchPhotoDataSource

public class SearchPhotoViewModel(query: String,order_by:String) : ViewModel() {

    var flow = Pager(PagingConfig(30)) {
        SearchPhotoDataSource(query,order_by)
    }.flow.cachedIn(viewModelScope)

}