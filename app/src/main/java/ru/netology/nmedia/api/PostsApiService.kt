package ru.netology.nmedia.api

import ru.netology.nmedia.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.netology.nmedia.dto.Post

interface PostsApiService {
    @GET("posts")
    suspend fun getAll(): Post

    @GET ("posts/{id}")
    suspend fun getById (@Path("id")id: Long): Post

    @POST("posts/{id}/likes")
    suspend fun likeById(@Path("id") id: Long): Post

    @DELETE("posts/{id}/likes")
    suspend fun dislikeById(@Path("id") id: Long): Post

    @DELETE("posts")
    suspend fun removeById()

    @POST("posts")
    suspend fun save(@Body post: Post): Post
}


object PostsApi {

    const val BASE_URL = "${BuildConfig.BASE_URL}/api/slow/"
    val retrofitService by lazy {
        val logger = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
        retrofit.create(PostsApiService::class.java)
    }
}