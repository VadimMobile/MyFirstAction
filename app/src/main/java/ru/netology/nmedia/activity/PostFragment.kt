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
                val id: Long = 0,
                val share: Long = 0,
                val likes: Long = 0,
                val content: String = "",
                val published: String = "",
                val author: String = "",
                val likedByMe: Boolean = false,
                val sharedByMe: Boolean = false,
                val video: String? = null,
            }
        }
    }
}