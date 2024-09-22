package com.example.homeworkattractions.view.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkattractions.databinding.LayerNewsBinding
import com.example.homeworkattractions.model.HomeItemList
import com.example.homeworkattractions.view.adapter.NewsItemAdapter

class NewsViewHolder(
    private val binding: LayerNewsBinding
) : BaseAdapterViewHolder<HomeItemList>(binding.root) {
    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun bindViews(item: HomeItemList) {
        if (item is HomeItemList.NewsItemList) {
            val newsAdapter = NewsItemAdapter(item.newsList)
            newsAdapter.setOnItemClickListener {
                onItemClickListener?.invoke(it)
            }
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = newsAdapter
            }
        }
    }
}
