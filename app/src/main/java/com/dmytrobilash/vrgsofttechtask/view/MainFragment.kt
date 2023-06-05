package com.dmytrobilash.vrgsofttechtask.view

import android.view.LayoutInflater
import android.os.Bundle
import androidx.fragment.app.viewModels
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.dmytrobilash.vrgsofttechtask.databinding.FragmentMainBinding
import com.dmytrobilash.vrgsofttechtask.viewmodel.MainFragmentViewModel


class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModels()
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
