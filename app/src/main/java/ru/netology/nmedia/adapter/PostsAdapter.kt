package ru.netology.nmedia.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.nmedia.R
import ru.netology.nmedia.Utils
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post


interface OnInteractionListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun noEdit(post: Post)
    fun onVideo(post: Post)
    fun onPost(post: Post) {}
}

class PostsAdapter(
    private val OnInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), OnInteractionListener
        )
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val OnInteractionListener: OnInteractionListener,
    private val urls: List<String> = listOf("netology.jpg", "sber.jpg", "tcs.jpg", "404.png"),
    private var index: Int = 0
) : RecyclerView.ViewHolder(binding.root) {

    init {
        val url = "http://10.0.2.2:9999/avatars/${urls[index++]}"
        Glide.with(binding.avatar)
            .load(url)
            .into(binding.avatar)
    }

    fun bind(post: Post) = with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        likes.isChecked = post.likedByMe
        likes.text = post.likes.toString()
        share.isChecked = post.sharedByMe
        share.isCheckable = !post.sharedByMe
        root.setOnClickListener {
            OnInteractionListener.onPost(post)
        }
        if (post.video == null) {
            binding.playVideoGroup.visibility = View.GONE
        } else {
            binding.playVideoGroup.visibility = View.VISIBLE
        }

        likes.setOnClickListener {
            OnInteractionListener.onLike(post)
        }

        share.setOnClickListener {
            OnInteractionListener.onShare(post)
        }

        playVideoGroup.setOnClickListener {
            OnInteractionListener.onVideo(post)
        }

        likes.text = Utils.formatCounter(post.likes)
        share.text = Utils.formatCounter(post.share)
        play.setOnClickListener { OnInteractionListener.onVideo(post) }
        backgroundVideo.setOnClickListener { OnInteractionListener.onVideo(post) }


        menu.setOnClickListener {
            var isMenuValid = false
            menu.postDelayed({ isMenuValid = true }, 500) // Разрешить действия через 0.5 секунды

            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_options)
                setOnMenuItemClickListener { item ->
                    if (isMenuValid) {
                        when (item.itemId) {
                            R.id.remove -> {
                                OnInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                OnInteractionListener.onEdit(post)
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

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}