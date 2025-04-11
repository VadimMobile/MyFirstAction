package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import ru.netology.nmedia.dto.Post

interface PostRepository {
    val data: LiveData <List<Post>>
    suspend fun getAllAsync(): List<Post>
    suspend fun saveAsync(post: Post): Post
    suspend fun likeByIdAsync(id: Long): Post
    suspend fun removeByIdAsync(id: Long)

}