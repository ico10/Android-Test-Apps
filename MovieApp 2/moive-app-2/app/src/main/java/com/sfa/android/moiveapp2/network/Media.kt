package com.sfa.android.moiveapp2.network

import android.os.Parcelable
import com.sfa.android.moiveapp2.network.moviedetailsmodel.Genre
import com.sfa.android.moiveapp2.network.moviedetailsmodel.Videos
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val id: Long,
    @Json(name = "poster_path")
    val posterFile: String,
    @Json(name = "media_type")
    private var type: String?,
    private val name: String?,
    private val title: String?,
    @Json(name = "release_date")
    private val releaseDate: String?,
    @Json(name = "first_air_date")
    private val firstAirDate: String?,
    val popularity: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    private val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val overview: String,
    @Json(name = "genres")
    val genres: List<Genre>?,
    val videos: Videos?
) : Parcelable {

    fun getMediaReleaseDate(): String {
        return "" + (releaseDate ?: firstAirDate)
    }

    fun getMediaTitle(): String {
        return "" + (name ?: title)
    }

    fun getMediaType(): String {
        return type ?: ""
    }

    fun setMediaType(mediaType: String) {
        type = mediaType
    }
}
