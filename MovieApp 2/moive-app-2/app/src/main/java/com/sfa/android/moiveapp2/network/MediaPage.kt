package com.sfa.android.moiveapp2.network

import com.squareup.moshi.Json

data class MediaPage(
    val page: Int,
    val results: List<Media>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)