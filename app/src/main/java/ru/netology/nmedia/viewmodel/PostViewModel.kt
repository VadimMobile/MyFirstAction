package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel(){

    private var repository = PostRepositoryInMemory()

    val post = repository.getPost()

    fun likes(){
        repository.likes()
    }

}