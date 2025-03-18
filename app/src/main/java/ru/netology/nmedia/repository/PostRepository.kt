package ru.netology.nmedia.repository

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(post: Post): Post
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun shareById(id: Long)

    fun getAllAsync(callback: GetAllCallback)
    fun saveAsync(callback: GetAllCallback)
    fun likeByIdAsync(callback: GetAllCallback)
    fun removeByIdAsync(callback: GetAllCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>)
        fun onError(e: Exception)

    } interface SaveCallback {
        fun onSuccess(posts: Post)
        fun onError(e: Exception)

    } interface LikeByIdCallback {
        fun onSuccess(posts: Post)
        fun onError(e: Exception)
    }

    interface RemoveByIdCallback {
        fun onSuccess(id: Long)
        fun onError(e: Exception)
    }
}