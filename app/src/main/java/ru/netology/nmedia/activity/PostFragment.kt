package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import androidx.fragment.app.viewModels
import ru.netology.nmedia.Utils
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val postId = arguments?.idArg?.toLongOrNull() ?: -1L
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likes.isChecked = post.likedByMe
                likes.text = Utils.formatCounter(post.likes)
                share.text = Utils.formatCounter(post.share)
                share.isChecked = !post.sharedByMe
                likes.setOnClickListener {
                    viewModel.likeById(post.id)
                }
                share.setOnClickListener {
                    viewModel.shareById(post.id)
                }
            }
        }
        return binding.root
    }

    companion object{
        var Bundle.idArg by StringArg
    }
}