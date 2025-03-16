package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

class PostRepositoryRoomImpl: PostRepository {

    private companion object {
        const val BASE_URL = "http://10.0.2.2:9999/"
        val  jsonType = "application/json".toMediaType()
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val postsType = object : TypeToken <List<Post>>() {}.type

    override fun getAll(): List<Post> {
        val request = Request.Builder()
            .get()
            .url("${BASE_URL}api/slow/posts")
            .build()

       val call = client.newCall(request)

        val response = call.execute()

       val responseBody = requireNotNull(response.body) {"Body is null"}

        return gson.fromJson(responseBody.string(), postsType)

    }

    override fun likeById(id: Long) = TODO()

    override fun save(post: Post): Post{
        val request = Request.Builder()
            .post(gson.toJson(post, Post::class.java).toRequestBody(jsonType))
            .url("${BASE_URL}api/slow/posts")
            .build()

        val call = client.newCall(request)

        val response = call.execute()

        val responseBody = requireNotNull(response.body) {"Body is null"}

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