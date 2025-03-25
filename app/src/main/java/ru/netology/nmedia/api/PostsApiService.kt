package ru.netology.nmedia.api

import ru.netology.nmedia.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.netology.nmedia.dto.Post

interface PostsApiService {
    @GET("posts")
    fun getAll(): Call<List<Post>>

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