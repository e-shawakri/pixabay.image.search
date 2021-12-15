package com.shawakri.pixabayimagesearch.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.shawakri.pixabayimagesearch.api.PixaBayApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixaBayRepo @Inject constructor(private val pixaBayApi: PixaBayApi) {

    fun getSearchResults(query: String) : LiveData<PagingData<PixaBayPhoto>> {

        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {PixaBayPagingSource(pixaBayApi, query)}
        ).liveData

        Log.d("getResponse", "getSearchResults: $pager")

        return pager
    }



}