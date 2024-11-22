package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {
    private val data = MutableLiveData<Post>(
        Post(
            id = 1,
            likes = 5679,
            share = 649,
            author =  "Нетология. Университет интернет-профессий будущего",
            //authorAvatar = "@sample/posts_avatars",
            published = "21 мая в 18:36",
            content =  "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )
    )

    override fun getPost(): LiveData<Post> = data



    override fun likes() {
        val currentPost = data.value ?: return
        val updatedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = if (currentPost.likedByMe) {currentPost.likes - 1} else {currentPost.likes + 1}
        )
        data.value = updatedPost
    }

    override fun share() {
        val currentPost = data.value ?: return
        val updatedPost = currentPost.copy(
            sharedByMe = true,
            share = currentPost.share + 1
        )
        data.value = updatedPost
    }
}