package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()

        viewModel.post.observe(this) { post ->
            binding.counterLikes.text = Utils.formatCounter(post.likes)
            binding.counterShare.text = Utils.formatCounter(post.share)
            binding.content.text = post.content
            binding.published.text = post.published
            binding.author.text = post.author
            binding.likes.setImageResource(
                if (post.likedByMe) {
                    R.drawable.baseline_favorite_24
                } else {
                    R.drawable.ic_favorite_border_24
                }
            )
            binding.likes.setOnClickListener {
                viewModel.likes()
            }
            binding.share.setOnClickListener {
                viewModel.share()
            }
            }
        }
    }
