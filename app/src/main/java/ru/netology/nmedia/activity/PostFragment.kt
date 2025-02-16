package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import androidx.fragment.app.viewModels
import ru.netology.nmedia.Utils
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
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
                menu.setOnClickListener {
                    var isMenuValid = false
                    menu.postDelayed({ isMenuValid = true }, 500) // Разрешить действия через 0.5 секунды

                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.menu_options)
                        setOnMenuItemClickListener { item ->
                            if (isMenuValid) {
                                when (item.itemId) {
                                    R.id.remove -> {
                                        viewModel.removeById(post.id)
                                        true
                                    }

                                    R.id.edit -> {
                                        viewModel.edit(post)
                                        true
                                    }

                                    else -> false
                                }
                            } else {
                                // Если меню не готово, игнорируем нажатие
                                false
                            }
                        }
                    }.show()
                }
            }
        }
        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id != 0L) {
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = it.content })
            }
        }
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_PostFragment_to_newPostFragment4)
        }
        return binding.root
    }
    companion object{
        var Bundle.idArg by StringArg
    }
}