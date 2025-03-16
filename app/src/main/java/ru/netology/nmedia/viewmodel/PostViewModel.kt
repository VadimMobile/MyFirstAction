package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryRoomImpl
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryRoomImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel> = _data
    var edited = MutableLiveData(empty)

    init {
        load()
    }

    fun saveContent(content: String) {
        thread {
            edited.value?.let {
                repository.save(it.copy(content = content))
            }
            edited.postValue(empty)
        }
    }

    fun load() {
        thread {
            _data.postValue(FeedModel(loading = true))
          try {
               val posts = repository.getAll()

                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (_: Exception){
                FeedModel(error = true)
            }.also(_data::postValue)
        }
    }

    fun likeById(id: Long) = repository.likeById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    fun edit(post: Post) {
        edited.value = post
    }

    fun noEdit() {
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