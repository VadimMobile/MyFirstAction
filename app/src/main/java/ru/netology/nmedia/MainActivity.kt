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
            binding.counterShare.text = post.counterShare.toString()
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


//            binding.share.setOnClickListener {
//                post.sharedByMe = !post.sharedByMe
//                binding.share.setImageResource(
//                    R.drawable.baseline_share_24
//                )
//            }
//            binding.share.setOnClickListener {
//                post.sharedByMe = !post.sharedByMe
//                post.counterShare++
//                binding.counterShare.text = post.counterShare.toString()
//                binding.counterShare.text = Utils.formatCounter(post.counterShare)
//            } Счётчик не работает так как указан неизменяемый тип, по дз нужно доделать


            //binding.root.setOnClickListener{Toast.makeText(this@MainActivity, "root", Toast.LENGTH_SHORT).show()}
            //binding.likes.setOnClickListener {Toast.makeText(this@MainActivity, "likes", Toast.LENGTH_SHORT).show()}
            //binding.avatar.setOnClickListener { Toast.makeText(this@MainActivity, "avatar", Toast.LENGTH_SHORT).show()}
            // Проверка обработчика со всплывающей иконкой в приложении.
        }
            binding.likes.setOnClickListener {
             viewModel.likes()
            }
    }
}