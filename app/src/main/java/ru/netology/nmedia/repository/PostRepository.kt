package ru.netology.nmedia.repository

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import ru.netology.nmedia.dto.Post

interface PostRepository {

    suspend fun getAllAsync(): List<Post>
    suspend fun saveAsync(post: Post): Post
    suspend fun likeByIdAsync(id: Long): Post
    suspend fun removeByIdAsync(id: Long)

}