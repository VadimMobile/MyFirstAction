package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.Utils
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post


interface OnInteractionListener{
    fun onLike (post: Post)
    fun onShare(post: Post)
    fun onRemove (post: Post)
    fun onEdit (post: Post)
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
            ),   OnInteractionListener
        )
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val OnInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        likes.setImageResource(
            if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.ic_favorite_border_24
        )
        likes.setOnClickListener {
            OnInteractionListener.onLike(post)
        }
        share.setImageResource(
            if (post.sharedByMe) R.drawable.baseline_share_24 else R.drawable.baseline_share_24
        )
        share.setOnClickListener {
            OnInteractionListener.onShare(post)
        }
        counterLikes.text = Utils.formatCounter(post.likes)
        counterShare.text = Utils.formatCounter(post.share)

        menu.setOnClickListener{
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_options)
                setOnMenuItemClickListener {
                    when (it.itemId) {
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
                }
            }.show()
        }
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}