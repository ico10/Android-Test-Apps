package com.sfa.android.moiveapp2.network.userlistmodels

import android.os.Parcelable
import com.sfa.android.moiveapp2.network.Media
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaList(
    val id: Long,
    val name: String,
    @Json(name = "iso_639_1")
    val iso: String = "en",
    @Json(name = "poster_path")
    val posterImage: String?,
    val results: List<Media>?
) : Parcelable


@Parcelize
data class SubmitableMediaList(
    val name: String,
    @Json(name = "iso_639_1")
    val iso: String = "en",
) : Parcelable
