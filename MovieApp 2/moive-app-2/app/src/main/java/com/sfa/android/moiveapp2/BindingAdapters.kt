package com.sfa.android.moiveapp2

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500"
private const val VIDEO_THUMBNAIL_BASE_START = "http://i3.ytimg.com/vi/"
private const val VIDEO_THUMBNAIL_BASE_END = "/maxresdefault.jpg"

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val image = BASE_IMAGE_URL + imgUrl
        val imgUri = image.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

@BindingAdapter("videoUrl")
fun bindThumbnail(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val image = VIDEO_THUMBNAIL_BASE_START + imgUrl + VIDEO_THUMBNAIL_BASE_END
        val imgUri = image.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}
