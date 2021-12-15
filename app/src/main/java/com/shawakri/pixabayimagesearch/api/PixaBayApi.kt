package com.shawakri.pixabayimagesearch.api

import com.example.pixabayimagesearch.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaBayApi {

    companion object {
        const val BASE_URL = "https://pixabay.com/"
        const val API_KEY = BuildConfig.PIXABAY_API_KEY
    }

    @GET("api/?key=$API_KEY&image_type=photo")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
        ) : PixaBayResponse
}