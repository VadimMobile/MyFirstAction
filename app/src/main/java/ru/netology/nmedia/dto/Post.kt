package ru.netology.nmedia.dto

data class Post (
    val id: Long = 0,
    var counterLikes: Long = 0,
    var counterShare: Long = 0,
    val content: String = "",
    val published: String = "",
    val author: String = "",
    var likedByMe: Boolean = false,
    var sharedByMe: Boolean = false
)
