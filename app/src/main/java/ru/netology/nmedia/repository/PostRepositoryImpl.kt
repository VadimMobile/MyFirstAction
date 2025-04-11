package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.entity.fromDto
import ru.netology.nmedia.entity.toDto

class PostRepositoryImpl(
    private val dao: PostDao,
) : PostRepository {
    override val data: LiveData<List<Post>> = dao.getAll().map { it.toDto() }

    override suspend fun getAllAsync() {
      val posts : List<Post> = PostsApi.retrofitService.getAll()
        dao.insert(posts.fromDto())
    }
    override suspend fun saveAsync(post: Post): Post = PostsApi.retrofitService.save(post)

    override suspend fun likeByIdAsync(id: Long): Post = PostsApi.retrofitService.likeById(id)

    override suspend fun removeByIdAsync(id: Long) {
        PostsApi.retrofitService.removeById(id)
    }


}