package com.shawakri.pixabayimagesearch.api

import com.shawakri.pixabayimagesearch.data.PixaBayPhoto

data class PixaBayResponse(
    val hits: List<PixaBayPhoto>
)