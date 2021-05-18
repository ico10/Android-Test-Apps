package com.sfa.android.moiveapp2.network.moviedetailsmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val type: String
) : Parcelable
