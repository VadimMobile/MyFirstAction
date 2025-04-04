package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel> = _data
    var edited = MutableLiveData(empty)

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit> = _postCreated

    private val _singleError = SingleLiveEvent<Unit>()
    val singleError: LiveData<Unit> = _singleError

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _data.postValue(FeedModel(loading = true))
            try {
                val posts = repository.getAllAsync()
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            } catch (e: Exception) {
                _data.postValue(FeedModel(loading = true))

            }
        }
    }

    fun save() {
        edited.value?.let {
            viewModelScope.launch {
                repository.saveAsync(it)
                _postCreated.value = Unit
            }
        }
        edited.value = empty
    }


    fun likeById(id: Long) {
     viewModelScope.launch {
         repository.likeByIdAsync(id) //TODO
     }
    }

    fun removeById(id: Long) {
        viewModelScope.launch {
            repository.removeByIdAsync(id) //TODO
        }
    }

   // fun shareById(id: Long) = repository.shareById(id)

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