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
import ru.netology.nmedia.util.SingleLiveEvent
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

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit> = _postCreated

    init {
        load()
    }

    fun saveContent(content: String) {
        thread {
            edited.value?.let {
                repository.save(it.copy(content = content))
                _postCreated.postValue(Unit)
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

    fun likeById(id: Long) {
        thread {
            val oldPost = data.value?.posts?.find { it.id == id } ?: return@thread
            val newPostState = repository.likeById(oldPost)
            val updatedPosts = _data.value?.posts.orEmpty().map { post ->
                if (post.id == id) {
                    post.copy(likedByMe = !post.likedByMe)
                } else {
                    post
                }
            }

            val finalUpdatedPosts = updatedPosts.map { post ->
                if (post.id == id) {
                    post.copy(likes = newPostState.likes)
                } else {
                    post
                }
            }
            _data.postValue(FeedModel(posts = finalUpdatedPosts))
        }
    }

    fun removeById(id: Long){
        thread {
            val oldState = _data.value

            val updatePosts = _data.value?.posts.orEmpty().filter {
                it.id != id
            }

            _data.postValue(FeedModel(posts = updatePosts))

            try {
                repository.removeById(id)
            } catch (_: Exception){
                _data.postValue(oldState)
            }
        }
    }


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