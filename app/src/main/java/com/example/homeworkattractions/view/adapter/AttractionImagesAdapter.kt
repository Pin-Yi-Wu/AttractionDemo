package com.example.homeworkattractions.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.ItemAttractionImageBinding
import com.example.homeworkattractions.model.Image

class AttractionImagesAdapter(private val images: List<Image>) :
    RecyclerView.Adapter<AttractionImagesAdapter.AttractionImageViewHolder>() {

    override fun getItemCount(): Int = if (images.isEmpty()) 1 else images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionImageViewHolder {
        val binding = ItemAttractionImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AttractionImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionImageViewHolder, position: Int) {
        if (images.isEmpty()) {
            holder.bindPlaceholder()
        } else {
            holder.bind(images[position])
        }
    }

    class AttractionImageViewHolder(private val binding: ItemAttractionImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            Glide.with(binding.root.context)
                .load(image.src)
                .into(binding.imgAttraction)
        }

        fun bindPlaceholder() {
            Glide.with(binding.root.context)
                .load(R.drawable.ic_svg_placeholder)
                .into(binding.imgAttraction)
        }
    }
}
