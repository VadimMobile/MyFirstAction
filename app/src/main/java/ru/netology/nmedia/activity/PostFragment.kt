package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val postId = arguments?.idArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            with(binding) {
                data class Post(
                val id: Long = 0,
                val share: Long = 0,
                val likes: Long = 0,
                val content: String = "",
                val published: String = "",
                val author: String = "",
                val likedByMe: Boolean = false,
                val sharedByMe: Boolean = false,
                val video: String? = null,
                )
                override fun likeById(id: Long) {
                    posts = posts.map {
                        if (it.id != id) it else it.copy(
                            likedByMe = !it.likedByMe,
                            likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                        )
                    }
                    data.value = posts
                }

                override fun shareById(id: Long) {
                    posts = posts.map {
                        if (it.id != id) it else it.copy(
                            sharedByMe = true,
                            share = it.share + 1
                        )
                    }
                    data.value = posts
                }
            }
        }
    }
}