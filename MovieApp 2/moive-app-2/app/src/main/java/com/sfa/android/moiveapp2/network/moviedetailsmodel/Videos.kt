package com.sfa.android.moiveapp2.network.moviedetailsmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Videos(
    val results: List<Video>
): Parcelable
