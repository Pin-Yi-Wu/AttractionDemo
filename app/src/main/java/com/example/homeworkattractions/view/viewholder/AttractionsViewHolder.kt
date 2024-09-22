package com.example.homeworkattractions.view.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkattractions.databinding.LayerAttractionsBinding
import com.example.homeworkattractions.model.HomeItemList
import com.example.homeworkattractions.view.adapter.AttractionItemAdapter

class AttractionsViewHolder(
    private val binding: LayerAttractionsBinding
) : BaseAdapterViewHolder<HomeItemList>(binding.root) {
    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun bindViews(item: HomeItemList) {
        if (item is HomeItemList.AttractionItemList) {
            val attractionAdapter = AttractionItemAdapter(item.attractionList)
            attractionAdapter.setOnItemClickListener {
                onItemClickListener?.invoke(it)
            }
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = attractionAdapter
            }
        }
    }
}
