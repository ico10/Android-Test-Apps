package com.sfa.android.moiveapp2.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sfa.android.moiveapp2.BuildConfig
import com.sfa.android.moiveapp2.network.moviedetailsmodel.Reviews
import com.sfa.android.moiveapp2.network.userlistmodels.MediaList
import com.sfa.android.moiveapp2.network.userlistmodels.MediaListItems
import com.sfa.android.moiveapp2.network.userlistmodels.MediaLists
import com.sfa.android.moiveapp2.network.userlistmodels.SubmitableMediaList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.themoviedb.org/"
private const val AUTH_BEARER = "Authorization: Bearer"
private const val CONTENT_TYPE = "Content-type:application/json;charset=utf-8"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {

    @GET("3/movie/popular")
    fun getPopularMoviesAsync(@Query("api_key") key: String): Deferred<MediaPage>

    @GET("3/tv/popular")
    fun getPopularSeriesAsync(@Query("api_key") key: String): Deferred<MediaPage>

    @GET("3/movie/{movie_id}")
    fun getMovieAsync(@Path("movie_id") id: Long, @Query("api_key") key: String, @Query("append_to_response") append: String): Deferred<Media>

    @GET("3/tv/{series_id}")
    fun getSeriesAsync(@Path("series_id") id: Long, @Query("api_key") key: String, @Query("append_to_response") append: String): Deferred<Media>

    @GET("3/movie/{movie_id}/reviews")
    fun getMovieReviewsAsync(@Path("movie_id") id: Long, @Query("api_key") key: String): Deferred<Reviews>

    @GET("3/tv/{series_id}/reviews")
    fun getSeriesReviewsAsync(@Path("series_id") id: Long, @Query("api_key") key: String): Deferred<Reviews>

    @Headers(
        "$AUTH_BEARER ${BuildConfig.MOVIE_DB_AT}",
        CONTENT_TYPE
    )
    @POST("4/list")
    fun createListAsync(@Body list: SubmitableMediaList, @Query("api_key") key: String) : Deferred<CreateList>

    @Headers(
        "$AUTH_BEARER ${BuildConfig.MOVIE_DB_AT}",
        CONTENT_TYPE
    )
    @GET("4/account/{account_id}/lists")
    fun getAllMediaListsAsync(@Path("account_id") id: String) : Deferred<MediaLists>

    @Headers(
        "$AUTH_BEARER ${BuildConfig.MOVIE_DB_AT}",
        CONTENT_TYPE
    )
    @GET("4/list/{list_id}")
    fun getSingleMediaListAsync(@Path("list_id") id: Long, @Query("api_key") key: String) : Deferred<MediaList>

    @Headers(
        "$AUTH_BEARER ${BuildConfig.MOVIE_DB_AT}",
        CONTENT_TYPE
    )
    @DELETE("4/list/{list_id}")
    fun deleteListAsync(@Path("list_id") id: Long) : Deferred<DeleteList>

    @Headers(
        "$AUTH_BEARER ${BuildConfig.MOVIE_DB_AT}",
        CONTENT_TYPE
    )
    @POST("4/list/{list_id}/items")
    fun addMediaItemsToListAsync(@Path("list_id") id: Long, @Body mediaListItems: MediaListItems, @Query("api_key") key: String): Deferred<MediaListItems>

    @Headers(
        "$AUTH_BEARER ${BuildConfig.MOVIE_DB_AT}",
        CONTENT_TYPE
    )
    @HTTP(method = "DELETE", hasBody = true,
        path = "4/list/{list_id}/items"
    )
    fun deleteMediaItemsFromListAsync(@Path("list_id") id: Long, @Body mediaListItems: MediaListItems, @Query("api_key") key: String): Deferred<DeleteList>
}

object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}
