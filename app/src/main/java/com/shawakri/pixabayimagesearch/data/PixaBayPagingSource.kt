package com.shawakri.pixabayimagesearch.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shawakri.pixabayimagesearch.api.PixaBayApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
class PixaBayPagingSource (
    private val pixaBayApi: PixaBayApi,
    private val query: String
        ): PagingSource<Int, PixaBayPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PixaBayPhoto> {

        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response =
                pixaBayApi.searchImages(query = query, page = position, perPage = params.loadSize)
            val photos = response.hits

            Log.d("getResponse", "load: $response")

            LoadResult.Page(
                data = photos,
                prevKey = if(position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            Log.d("getResponse", "IOException: $exception")
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            Log.d("getResponse", "HttpException: $exception")
            LoadResult.Error(exception)
        }
    }
}