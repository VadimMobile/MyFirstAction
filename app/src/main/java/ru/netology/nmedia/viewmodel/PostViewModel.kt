package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
id = 0,
author = "",
content = "",
published = "",
likedByMe = false
)

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.getAll()

    private var edited = MutableLiveData(empty)

    fun  saveContent(content: String) {
        edited.value?.let {
            repository.save(it.copy(content = content))
        }
        edited.value = empty
    }

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun edit(post: Post){
        edited.value = post
    }

    fun noEdit (){
        edited.value = empty
    }

    fun checkContent(content: String) {
        edited.value?.let {
            val trimmed = content.trim()
            if (trimmed == it.content) {
                return
            }
            edited.value = it.copy(content = trimmed)
        }
    }


}