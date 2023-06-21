package com.dmytrobilash.vrgsofttechtask.view

import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dmytrobilash.vrgsofttechtask.R
import com.dmytrobilash.vrgsofttechtask.adapter.Adapter
import com.dmytrobilash.vrgsofttechtask.databinding.FragmentMainBinding
import com.dmytrobilash.vrgsofttechtask.viewmodel.RedditPostsViewModel
import com.dmytrobilash.vrgsofttechtask.viewmodel.RedditPostsViewModelFactory

class RedditPostsFragment : Fragment() {

    private val viewModel by viewModels<RedditPostsViewModel> { RedditPostsViewModelFactory() }
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: Adapter
    private lateinit var progressBar: ProgressBar
    private lateinit var image: ImageView

    private var after: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRecyclerView()
        fetchPosts()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Adapter.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retry image download
                adapter.retryImageDownload()
            } else {
                // Permission denied, show a message or handle it as per your requirement
                Toast.makeText(requireContext(), binding.root.context.getText(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViews() {
        progressBar = binding.progressBar
        image = binding.imageView
        adapter = Adapter { fetchPostsForPagination() }
        adapter.setImage(image)
    }

    private fun fetchPosts() {
        progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.setList(posts)
            progressBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        binding.rv.adapter = adapter
    }

    private fun fetchPostsForPagination() {
        after = adapter.getAfter()
        viewModel.fetchPostsForPagination(limit = 5, after = after!!)
    }
}
