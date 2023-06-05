package com.dmytrobilash.vrgsofttechtask.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dmytrobilash.vrgsofttechtask.R
import com.dmytrobilash.vrgsofttechtask.databinding.PostItemBinding
import com.dmytrobilash.vrgsofttechtask.model.domain.RedditPostModel
import java.util.*
import java.util.concurrent.TimeUnit

class Adapter(private val onPagination: () -> Unit) : RecyclerView.Adapter<Adapter.PostViewHolder>() {

    private var posts = emptyList<RedditPostModel>()
    private lateinit var imageView: ImageView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])

        //when last item the new items loads to the rv
        if (position == posts.size - 1) {
            Log.v("TagTag", position.toString())
            onPagination.invoke()
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    //holder class to hold the views of each item in the RecyclerView
    inner class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.image.setOnClickListener {
                val imageUrl = if (posts[adapterPosition].url.contains("v.redd.it")) {
                    posts[adapterPosition].thumbnail.replace("amp;", "")
                } else {
                    posts[adapterPosition].url
                }
                imageView.visibility = View.VISIBLE

                Glide.with(binding.root)
                    .load(imageUrl)
                    .into(imageView)
            }
            imageView.setOnClickListener {
                imageView.visibility = View.GONE
            }
        }

        //bindong data
        @SuppressLint("SetTextI18n")
        fun bind(post: RedditPostModel) {
            val context = binding.root.context
            binding.title.text = post.title
            binding.author.text =
                context.getString(R.string.posted_by) + " " + post.author + " " + TimeUnit.SECONDS.toHours((Date().time / 1000 - post.createdTime)) + " hours ago"
            binding.commentsQuantity.text = post.comQuantity.toString() + " " + context.getString(R.string.comments)

            val imageUrl = if (post.url.contains("v.redd.it")) {
                post.thumbnail.replace("amp;", "")
            } else {
                post.url
            }

            Glide.with(binding.root)
                .load(imageUrl)
                .placeholder(R.drawable.imageholder)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.image)
        }
    }

    fun setImage(image: ImageView) {
        imageView = image
    }

    fun setList(list: List<RedditPostModel>) {
        posts = list
        notifyDataSetChanged()
    }

    //get the name of the last item in the list
    fun getAfter(): String {
        return posts.lastOrNull()?.name ?: ""
    }
}