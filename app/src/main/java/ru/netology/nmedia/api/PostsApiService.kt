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
import ru.netology.nmedia.dto.Post

interface PostsApiService {
    @GET("posts")
    fun getAll(): Call<List<Post>>
    @POST("posts")
    fun likeById(): Call<List<Post>>
    @POST("posts")
    fun  dislikeById(): Call<List<Post>>
    @DELETE("posts")
    fun removeById(): Call<Unit>
    @POST("posts")
    fun save(@Body post: POST): Call<Post>
}


object PostsApi{

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