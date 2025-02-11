package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R

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
                author = post.author
                published = post.published
                content = post.content
                likes = post.likedByMe
                likes = post.likes.toString()
                share = post.sharedByMe
                share = !post.sharedByMe
                override fun likeById(id: Long) {
                    likes.setOnClickListener {
                        OnInteractionListener.onLike(post)
                    }
                    share.setOnClickListener {
                        OnInteractionListener.onShare(post)
                    }
                }
            }
        }
        binding.save.setOnClickListener {
            findNavController().navigate(R.id.action_PostFragment_to_newPostFragment)
        }
        return binding.root
    }
}