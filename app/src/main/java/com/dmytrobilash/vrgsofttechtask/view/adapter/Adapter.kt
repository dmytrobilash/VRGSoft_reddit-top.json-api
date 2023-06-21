package com.dmytrobilash.vrgsofttechtask.view.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
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
import com.dmytrobilash.vrgsofttechtask.model.RedditPostUIModel
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class Adapter(private val onPagination: () -> Unit) : RecyclerView.Adapter<Adapter.PostViewHolder>() {

    private var posts = emptyList<RedditPostUIModel>()
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
        if (position == posts.size-1) {
            onPagination.invoke()
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    //holder class to hold the views of each item in the RecyclerView
    inner class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            //image post click
            binding.image.setOnClickListener {
                val imageUrl: String? = when {
                    posts[adapterPosition].url.contains("v.redd.it") -> {
                        posts[adapterPosition].thumbnail.replace("amp;", "")
                    }

                    posts[adapterPosition].url.contains("i.redd.it") -> {
                        posts[adapterPosition].url
                    }
                    else -> {
                        null
                    }
                }
                //image bigger click
                imageView.visibility = View.VISIBLE

                //loading pictures
                if (imageUrl != null) {
                    Glide.with(binding.root)
                        .load(imageUrl)
                        .into(imageView)
                } else {
                    Glide.with(binding.root)
                        .load("")
                        .placeholder(R.drawable.imageholder)
                        .into(imageView)
                    binding.image.setImageResource(R.drawable.imageholder)
                }
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

                try {
                    downloadImage(currentImageUrl!!, binding.root.context)
                } catch (e: Exception) {
                    (binding.root.context as? Activity)?.runOnUiThread {
                        Toast.makeText(
                            binding.root.context,
                            binding.root.context.getText(R.string.fail_to_download_image),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        //binding data
        @SuppressLint("SetTextI18n")
        fun bind(post: RedditPostUIModel) {

            //clear glide to avoid the duplication of images in rv
            Glide.with(binding.root)
                .clear(binding.image)

            val context = binding.root.context
            binding.title.text = post.title
            val hoursAgo = TimeUnit.SECONDS.toHours((Date().time / 1000 - post.createdTime))
            val timeUnitString = when {
                hoursAgo in 0L..1L -> context.getString(R.string.hour_ago)
                hoursAgo < 24L -> context.getString(R.string.hours_ago)
                hoursAgo in 24L..47 -> context.getString(R.string.day_ago)
                else -> context.getString(R.string.days_ago)
            }
            val numComment = when (post.comQuantity) {
                1 -> context.getString(R.string.comment)
                else -> context.getString(R.string.comments)
            }
            binding.author.text =
                context.getString(R.string.posted_by) + " " + post.author + " " + hoursAgo + " " + timeUnitString
            binding.commentsQuantity.text = post.comQuantity.toString() + " " + numComment


            val imageUrl = when {
                post.url.contains("v.redd.it") -> {
                    post.thumbnail.replace("amp;", "") //get thumbnail of image
                }

                post.url.contains("i.redd.it") -> {
                    post.url //get image url
                }

                else -> {
                    null //if there is not the thumbnail or image -> url is null
                }
            }
            if (imageUrl != null) {
                Glide.with(binding.root)
                    .load(imageUrl)
                    .placeholder(R.drawable.imageholder)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .dontAnimate()
                    .into(binding.image)
            }
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
                    Toast.makeText(context, context.getString(R.string.success_to_download_image), Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                (context as? Activity)?.runOnUiThread {
                    Toast.makeText(context, context.getString(R.string.fail_to_download_image), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun retryImageDownload() {
        val post = posts.find { post -> post.url == currentImageUrl }
        post?.let { currentImageUrl?.let {it -> downloadImage(it, imageView.context) } }
    }

    fun setImage(image: ImageView) {
        imageView = image
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<RedditPostUIModel>) {
        posts = list
        notifyDataSetChanged()
    }

    //get the name of the last item in the list
    fun getAfter(): String {
        return posts.lastOrNull()?.name ?: ""
    }
}