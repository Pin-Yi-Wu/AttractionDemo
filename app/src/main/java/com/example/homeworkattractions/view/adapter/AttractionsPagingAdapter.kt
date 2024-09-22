package com.example.homeworkattractions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.ItemAttractionBinding
import com.example.homeworkattractions.model.Attraction

class AttractionsPagingAdapter :
    PagingDataAdapter<Attraction, AttractionsPagingAdapter.AttractionViewHolder>(
        AttractionDiffCallback()
    ) {
    private var onItemClickListener: ((Attraction) -> Unit)? = null

    fun setOnItemClickListener(listener: (Attraction) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val binding =
            ItemAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttractionViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        val attractionItem = getItem(position)
        attractionItem?.let {
            holder.bind(it)
        }
    }

    class AttractionViewHolder(
        private val binding: ItemAttractionBinding,
        private val onItemClickListener: ((Attraction) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: Attraction) {
            binding.textTitle.text = attraction.name
            binding.textContent.text = attraction.introduction
            Glide.with(binding.root.context).load(attraction.images.elementAtOrNull(0)?.src)
                .placeholder(
                    R.drawable.ic_svg_placeholder
                ).into(binding.imgAttraction)
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(attraction)
            }
        }
    }

    class AttractionDiffCallback : DiffUtil.ItemCallback<Attraction>() {
        override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem == newItem
        }
    }
}
