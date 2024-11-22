package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val post = Post(
            id = 1,
            counterLikes = 2999999,
            counterShare = 999,
            author =  "Нетология. Университет интернет-профессий будущего",
        //authorAvatar = "@sample/posts_avatars",
            published = "21 мая в 18:36",
        content =  "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"
        )

        binding.counterLikes.text = post.counterLikes.toString()
        binding.counterShare.text = post.counterShare.toString()
        binding.content.text = post.content
        binding.published.text = post.published
        binding.author.text = post.author
        binding.likes.setImageResource(
            if (post.likedByMe){
                R.drawable.baseline_favorite_24
                }else{
                R.drawable.ic_favorite_border_24
            }
        )

        binding.likes.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.likes.setImageResource(
                if (post.likedByMe){
                    R.drawable.baseline_favorite_24
                }else{
                    R.drawable.ic_favorite_border_24
                }
            )
        }

        binding.likes.setOnClickListener {
            post.likedByMe = !post.likedByMe
         if (post.likedByMe) post.counterLikes++ else post.counterLikes--
        binding.counterLikes.text = post.counterLikes.toString()
            binding.likes.setImageResource(
                if (post.likedByMe){
                    R.drawable.baseline_favorite_24
                }else{
                    R.drawable.ic_favorite_border_24
                }
            )
            binding.counterLikes.text = Utils.formatCounter(post.counterLikes)
        }


        binding.share.setOnClickListener {
            post.sharedByMe = !post.sharedByMe
            binding.share.setImageResource(
                R.drawable.baseline_share_24
            )
        }
        binding.share.setOnClickListener {
            post.sharedByMe = !post.sharedByMe
         post.counterShare++
        binding.counterShare.text = post.counterShare.toString()
            binding.counterShare.text = Utils.formatCounter(post.counterShare)
            }
        //binding.root.setOnClickListener{Toast.makeText(this@MainActivity, "root", Toast.LENGTH_SHORT).show()}
        //binding.likes.setOnClickListener {Toast.makeText(this@MainActivity, "likes", Toast.LENGTH_SHORT).show()}
        //binding.avatar.setOnClickListener { Toast.makeText(this@MainActivity, "avatar", Toast.LENGTH_SHORT).show() }
    }
}