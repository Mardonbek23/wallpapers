package com.example.wallpaper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.wallpaper.repository.PhotoDataSource

public class PhotoViewModel : ViewModel() {
    var flow = Pager(PagingConfig(30)) {
        PhotoDataSource()
    }.flow.cachedIn(viewModelScope)

}