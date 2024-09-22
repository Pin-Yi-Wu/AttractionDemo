package com.example.homeworkattractions.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkattractions.view.viewholder.BaseAdapterViewHolder

abstract class BaseRecyclerViewAdapter<Data, VH : BaseAdapterViewHolder<Data>> :
    RecyclerView.Adapter<VH>() {
}