package com.example.homeworkattractions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkattractions.databinding.ItemNewsBinding
import com.example.homeworkattractions.model.NewsItem
import com.example.homeworkattractions.util.UiUtil

class NewsPagingAdapter :
    PagingDataAdapter<NewsItem, NewsPagingAdapter.NewsViewHolder>(NewsDiffCallback()) {
    private var onItemClickListener: ((NewsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (NewsItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)
        newsItem?.let {
            holder.bind(it)
        }
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onItemClickListener: ((NewsItem) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsItem) {
            binding.textTitle.text = newsItem.title
            binding.textContent.text = newsItem.description
            binding.textPostTime.text = UiUtil.formatDateString(newsItem.posted, UiUtil.dateFormat1)
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(newsItem)
            }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }
    }
}
