package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    init {
        load()
    }

    fun load() {
        _data.postValue(FeedModel(loading = true))
        repository.getAllAsync(object : PostRepository.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun saveContent(content: String) {

        repository.saveAsync(
            edited.value?.copy(content = content) ?: return, // добавили content
            object : PostRepository.SaveCallback {
                override fun onSuccess(post: Post) {
                    val oldPosts = _data.value?.posts.orEmpty()
                    val updatedPosts = if (oldPosts.map { it.id }.contains(post.id)) // Проверяем есть ли пост с таким id в списке
                        oldPosts.map { if (it.id == post.id) post else it } // Если есть, то меняем
                    else listOf(post) + oldPosts // иначе - добавляем в начало
                    _data.postValue(FeedModel(posts = updatedPosts))
                    _postCreated.postValue(Unit) // уведомляем о том, что данные получены
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }
            })
        edited.postValue(empty)

    }

    fun likeById(id: Long) {
        val oldPost = data.value?.posts?.find { it.id == id } ?: return
        repository.likeByIdAsync(oldPost, object : PostRepository.LikeByIdCallback {
            override fun onSuccess(post: Post) {
                _data.postValue(
                    FeedModel(
                        posts = _data.value?.posts.orEmpty().map {
                            if (it.id == id) post
                            else it
                        }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun removeById(id: Long) {

        val updatePosts = _data.value?.posts.orEmpty().filter {
            it.id != id
        }

        _data.postValue(FeedModel(posts = updatePosts))
        repository.removeByIdAsync(id, object : PostRepository.RemoveByIdCallback {
            override fun onSuccess(id: Long) {
                _data.postValue(FeedModel(posts = updatePosts))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
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