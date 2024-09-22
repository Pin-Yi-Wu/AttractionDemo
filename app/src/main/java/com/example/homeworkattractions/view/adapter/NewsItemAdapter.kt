package com.example.homeworkattractions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkattractions.databinding.ItemNewsBinding
import com.example.homeworkattractions.model.NewsItem
import com.example.homeworkattractions.util.UiUtil

class NewsItemAdapter(
    private val newsList: List<NewsItem>
) : RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    class NewsItemViewHolder(
        private val binding: ItemNewsBinding,
        private val onItemClickListener: ((position: Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItem: NewsItem) {
            binding.textTitle.text = newsItem.title
            binding.textPostTime.text = UiUtil.formatDateString(newsItem.posted, UiUtil.dateFormat1)
            binding.textContent.text = newsItem.description
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsItemViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
