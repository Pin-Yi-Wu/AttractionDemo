package com.example.homeworkattractions.view.viewholder

import com.example.homeworkattractions.databinding.ViewEmptyLayoutBinding
import com.example.homeworkattractions.model.HomeItemList

class EmptyViewHolder(
    binding: ViewEmptyLayoutBinding
) : BaseAdapterViewHolder<HomeItemList>(binding.root) {

    override fun bindViews(data: HomeItemList) {
    }
}
