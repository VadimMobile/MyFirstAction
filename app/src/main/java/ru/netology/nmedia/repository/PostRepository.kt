package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
fun getPost(): LiveData<Post>
    fun likes()
    fun share()
}