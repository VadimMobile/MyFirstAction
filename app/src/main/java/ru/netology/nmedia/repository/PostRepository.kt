package ru.netology.nmedia.repository

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(post: Post): Post
    fun dislikeById(post: Post): Post
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun shareById(id: Long)

    fun getAllAsync(callback: GetAllCallback)
    fun saveAsync(post: Post, callback: SaveCallback)
    fun likeByIdAsync(post: Post, callback: LikeByIdCallback)
    fun dislikeByIdAsync(post: Post, callback: LikeByIdCallback)
    fun removeByIdAsync(id: Long, callback: RemoveByIdCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>)
        fun onError(e: Exception)

    }

    interface SaveCallback {
        fun onSuccess(posts: Post)
        fun onError(e: Exception)

    }

    interface LikeByIdCallback {
        fun onSuccess(posts: Post)
        fun onError(e: Exception)
    }

    interface DislikeByIdCallback {
        fun onSuccess(posts: Post)
        fun onError(e: Exception)
    }

    interface RemoveByIdCallback {
        fun onSuccess(id: Long)
        fun onError(e: Exception)
    }
}