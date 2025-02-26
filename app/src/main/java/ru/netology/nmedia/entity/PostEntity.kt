package ru.netology.nmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo
    val share: Long = 0,
    val likes: Long = 0,
    val content: String = "",
    val published: String = "",
    val author: String = "",
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val video: String? = null,
    ) {
    fun toDto() = Post(id, share, likes, content, published, author, likedByMe)

    companion object {
        fun fromDto(post: Post) = PostEntity(post.id, post.share, post.likes,
            post.content, post.published, post.author, post.likedByMe
        )

    }
}
