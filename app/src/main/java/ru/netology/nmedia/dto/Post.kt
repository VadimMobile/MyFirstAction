package ru.netology.nmedia.dto

data class Post (
    val id: Long = 0,
    val share: Long = 0,
    val likes: Long = 0,
    val content: String = "",
    val published: String = "",
    val author: String = "",
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false
)
