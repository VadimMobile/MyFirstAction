package ru.netology.nmedia.repository

import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post

class PostRepositoryImpl : PostRepository {
    override suspend fun getAllAsync(): List<Post> = PostsApi.retrofitService.getAll()

    override suspend fun saveAsync(post: Post): Post = PostsApi.retrofitService.save(post)

    override suspend fun likeByIdAsync(id: Long): Post = PostsApi.retrofitService.likeById(id)

    override suspend fun removeByIdAsync(id: Long) {
        PostsApi.retrofitService.removeById(id)
    }


}