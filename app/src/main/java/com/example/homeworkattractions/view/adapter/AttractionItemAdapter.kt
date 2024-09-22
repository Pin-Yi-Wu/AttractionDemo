package com.example.homeworkattractions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.ItemAttractionBinding
import com.example.homeworkattractions.model.Attraction

class AttractionItemAdapter(
    private val attractionList: List<Attraction>
) : RecyclerView.Adapter<AttractionItemAdapter.AttractionItemViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    class AttractionItemViewHolder(
        private val binding: ItemAttractionBinding,
        private val onItemClickListener: ((position: Int) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: Attraction) {
            binding.textTitle.text = attraction.name
            binding.textContent.text = attraction.introduction
            Glide.with(binding.root.context).load(attraction.images.elementAtOrNull(0)?.src)
                .placeholder(R.drawable.ic_svg_placeholder).into(binding.imgAttraction)
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAttractionBinding.inflate(inflater, parent, false)
        return AttractionItemViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: AttractionItemViewHolder, position: Int) {
        holder.bind(attractionList[position])
    }

    override fun getItemCount(): Int {
        return attractionList.size
    }
}
