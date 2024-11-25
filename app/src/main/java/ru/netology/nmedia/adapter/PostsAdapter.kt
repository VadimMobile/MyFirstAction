package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias LikeCallback = (Post)-> Unit
typealias ShareCallback = (Post)-> Unit
class PostsAdapter(private val callbackLike: LikeCallback, private val callbackShare: ShareCallback) : ListAdapter<Post, PostViewHolder> (PostDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(CardPostBinding.inflate(LayoutInflater.from(parent.context), parent,false), callbackLike,callbackShare)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
holder.bind(getItem(position))    }
}

class PostViewHolder(private val binding: CardPostBinding, private val callbackLike: LikeCallback,
                     private val callbackShare: ShareCallback) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
    likes.setImageResource(
           if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.ic_favorite_border_24
       )
        likes.setOnClickListener {
            callbackLike(post)
        }
        share.setImageResource(
            if (post.sharedByMe) R.drawable.baseline_share_24 else R.drawable.baseline_share_24
        )
        share.setOnClickListener {
            callbackShare(post)
        }
        counterLikes.text = post.likes.toString()
        counterShare.text = post.share.toString()
    }
}

object PostDiffCallback: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}