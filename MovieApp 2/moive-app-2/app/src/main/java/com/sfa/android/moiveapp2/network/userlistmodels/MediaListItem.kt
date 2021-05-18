package com.sfa.android.moiveapp2.network.userlistmodels

import com.squareup.moshi.Json

data class MediaListItem(
    @Json(name = "media_id")
    val id: Long,
    @Json(name = "media_type")
    val mediaType: String
)
