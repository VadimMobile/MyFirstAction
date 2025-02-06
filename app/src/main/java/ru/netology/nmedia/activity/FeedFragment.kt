package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = :: requireParentFragment)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))

                startActivity(shareIntent)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun noEdit(post: Post) {
                viewModel.noEdit()
            }

            override fun onVideo(post: Post) {
                val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intentVideo)
            }

        })
        binding.container.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val new = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (new) {
                    binding.container.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id != 0L) {
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = it.content })

            }
        }

        binding.save.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}