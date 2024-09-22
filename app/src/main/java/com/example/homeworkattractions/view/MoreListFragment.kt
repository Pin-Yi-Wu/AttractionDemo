package com.example.homeworkattractions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.FragmentMoreListBinding
import com.example.homeworkattractions.view.adapter.AttractionsPagingAdapter
import com.example.homeworkattractions.view.adapter.HomeListAdapter
import com.example.homeworkattractions.view.adapter.NewsPagingAdapter
import com.example.homeworkattractions.viewmodel.MoreListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoreListFragment : BaseFragment() {
    private lateinit var newsAdapter: NewsPagingAdapter
    private lateinit var attractionsAdapter: AttractionsPagingAdapter
    private val viewModel: MoreListViewModel by viewModels()
    private val args: MoreListFragmentArgs by navArgs()

    private val binding by lazy {
        FragmentMoreListBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initObserver()
        initView()

        when (args.viewType) {
            HomeListAdapter.DataType.NEWS -> {
                viewModel.fetchPagedNews()
            }

            HomeListAdapter.DataType.ATTRACTIONS -> {
                viewModel.fetchPagedAttractions()
            }
        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.layoutManager = layoutManager

        if (args.viewType == HomeListAdapter.DataType.NEWS) {
            newsAdapter = NewsPagingAdapter()
            binding.rvList.adapter = newsAdapter
            newsAdapter.setOnItemClickListener { it ->
                val action = it.let {
                    MoreListFragmentDirections.actionMoreListFragmentToWebFragment(
                        context?.getString(R.string.home_news_title) ?: it.title, it.url
                    )
                }
                action.let { findNavController().navigate(it) }
            }
        } else {
            attractionsAdapter = AttractionsPagingAdapter()
            binding.rvList.adapter = attractionsAdapter
            attractionsAdapter.setOnItemClickListener { it ->
                val action = it.let {
                    MoreListFragmentDirections.actionMoreListFragmentToAttractionDetailFragment(
                        it
                    )
                }
                action.let { findNavController().navigate(it) }
            }
        }
        binding.viewToolbar.backClickListener = {
            findNavController().popBackStack()
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagedNews?.collectLatest {
                    newsAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagedAttractions?.collectLatest {
                    attractionsAdapter.submitData(it)
                }
            }
        }

        viewModel.isViewLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog() else dismissLoadingDialog()
        }
    }
}
