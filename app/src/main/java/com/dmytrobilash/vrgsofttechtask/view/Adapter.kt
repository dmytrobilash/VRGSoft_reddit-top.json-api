package com.dmytrobilash.vrgsofttechtask.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.dmytrobilash.vrgsofttechtask.R
import com.dmytrobilash.vrgsofttechtask.databinding.PostItemBinding
import com.dmytrobilash.vrgsofttechtask.model.domain.RedditPostModel
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class Adapter(private val onPagination: () -> Unit) : RecyclerView.Adapter<Adapter.PostViewHolder>() {

    private var posts = emptyList<RedditPostModel>()
    private lateinit var imageView: ImageView
    private var currentImageUrl: String? = null

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
            binding.download.setOnClickListener {
                val post = posts[adapterPosition]

                currentImageUrl = when {
                    post.url.contains("v.redd.it") -> {
                        post.thumbnail.replace("amp;", "") //load thumbnail
                    }
                    post.url.contains("i.redd.it") -> {
                        post.url
                    }
                    else -> {
                        null
                    }
                }
                Log.v("TAG", currentImageUrl.toString())
                try {
                    downloadImage(currentImageUrl!!, binding.root.context)
                }catch (e: Exception){
                    (binding.root.context as? Activity)?.runOnUiThread {
                        Toast.makeText(binding.root.context, "Failed to save image", Toast.LENGTH_SHORT).show()
                    }
                }
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

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private fun downloadImage(imageUrl: String, context: Context) {
        //check the required permissions are granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //request the permission
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {

            startImageDownload(imageUrl, context)
        }
    }
    private fun startImageDownload(imageUrl: String, context: Context) {

        // download the image
        Glide.with(context)
            .asFile()
            .load(imageUrl)
            .listener(object : RequestListener<File> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<File>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: File?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<File>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    resource?.let {
                        saveImageToStorage(it, context)
                    }
                    return false
                }
            })
            .submit()
    }

    private fun saveImageToStorage(imageFile: File?, context: Context) {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val destinationFile = File(downloadsDir, fileName)

        imageFile?.let {
            try {
                it.copyTo(destinationFile, overwrite = true)
                (context as? Activity)?.runOnUiThread {
                    Toast.makeText(context, "Image saved to Downloads", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                (context as? Activity)?.runOnUiThread {
                    Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun retryImageDownload() {
        val post = posts.find { post -> post.url == currentImageUrl }
        post?.let { currentImageUrl?.let { it -> downloadImage(it, imageView.context) } }
    }

    fun setImage(image: ImageView) {
        imageView = image
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<RedditPostModel>) {
        posts = list
        notifyDataSetChanged()
    }

    //get the name of the last item in the list
    fun getAfter(): String {
        return posts.lastOrNull()?.name ?: ""
    }
}