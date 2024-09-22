package com.example.homeworkattractions.view.viewholder

import com.example.homeworkattractions.databinding.LayerTitleBinding
import com.example.homeworkattractions.model.HomeItemList

class TitleViewHolder(
    private val binding: LayerTitleBinding,
) : BaseAdapterViewHolder<HomeItemList>(binding.root) {
    private var onItemClickMoreListener: ((Int?) -> Unit)? = null

    fun setOnItemClickMoreListener(listener: (Int?) -> Unit) {
        onItemClickMoreListener = listener
    }

    override fun bindViews(item: HomeItemList) {
        if (item is HomeItemList.HomeTitle) {
            binding.textTitle.text = item.title
            binding.imgMore.setOnClickListener {
                onItemClickMoreListener?.invoke(item.viewType)
            }
        }
    }
}
