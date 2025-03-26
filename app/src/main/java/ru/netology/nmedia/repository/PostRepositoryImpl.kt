package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.util.concurrent.TimeUnit

class PostRepositoryImpl : PostRepository {

    private companion object {
        const val BASE_URL = "http://10.0.2.2:9999/"
        val jsonType = "application/json".toMediaType()
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val postsType = object : TypeToken<List<Post>>() {}.type

    override fun getAllAsync(callback: PostRepository.GetAllCallback) {
        PostsApi.retrofitService.getAll().enqueue(object : Callback<List<Post>> {

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful){
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }else{
                    callback.onError(RuntimeException("${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<Post>>, e: Throwable) {
                callback.onError(Exception(e))
            }

        })
    }

    override fun likeByIdAsync(post: Post, callback: PostRepository.LikeByIdCallback) {
        PostsApi.retrofitService.likeById(post.id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response body is null"))
                    }
                } else {
                    callback.onError(RuntimeException("${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Post>, e: Throwable) {
                callback.onError(Exception(e))
            }
        })
    }


    override fun dislikeByIdAsync(post: Post, callback: PostRepository.LikeByIdCallback) {
        PostsApi.retrofitService.dislikeById(post.id).enqueue(object : Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response body is null"))
                    }
                } else {
                    callback.onError(RuntimeException("${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Post>, e: Throwable) {
                callback.onError(Exception(e))
            }
        })

    }

    override fun saveAsync(post: Post, callback: PostRepository.SaveCallback) {
        PostsApi.retrofitService.save(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful){
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }else{
                    callback.onError(RuntimeException("${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Post>, e: Throwable) {
                callback.onError(Exception(e))
            }
        })

    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.RemoveByIdCallback) {
        PostsApi.retrofitService.removeById().enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful){
                    callback.onSuccess(0)
                }else{
                    callback.onError(RuntimeException("${response.code()}: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Unit>, e: Throwable) {
                callback.onError(Exception(e))
            }
        })

    }

    override fun getAll(): List<Post> {
        val request = Request.Builder()
            .url("${BASE_URL}api/slow/posts")
            .build()

        val call = client.newCall(request)

        val response = call.execute()

        val responseBody = requireNotNull(response.body) { "Body is null" }

        return gson.fromJson(responseBody.string(), postsType)

    }

    override fun likeById(post: Post): Post {
        val method = if (post.likedByMe) "DELETE" else "POST"
        val request = Request.Builder()
            .method(method, gson.toJson(post, Post::class.java).toRequestBody(jsonType))
            .url("${BASE_URL}api/posts/${post.id}/likes")
            .build()

        val call = client.newCall(request)

        val response = call.execute()

        val responseBody = requireNotNull(response.body) { "Body is null" }

        return gson.fromJson(responseBody.string(), Post::class.java)
    }

    override fun dislikeById(post: Post): Post {
        TODO("Not yet implemented")
    }

    override fun save(post: Post): Post {
        val request = Request.Builder()
            .post(gson.toJson(post, Post::class.java).toRequestBody(jsonType))
            .url("${BASE_URL}api/slow/posts")
            .build()

        val call = client.newCall(request)

        val response = call.execute()

        val responseBody = requireNotNull(response.body) { "Body is null" }

        return gson.fromJson(responseBody.string(), Post::class.java)
    }

    override fun removeById(id: Long) {
        val request = Request.Builder()
            .delete()
            .url("${BASE_URL}api/slow/posts/$id")
            .build()

        val call = client.newCall(request)

        call.execute()
    }

    override fun shareById(id: Long) = TODO()
}