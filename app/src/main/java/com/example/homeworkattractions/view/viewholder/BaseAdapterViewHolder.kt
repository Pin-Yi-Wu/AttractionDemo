package com.example.homeworkattractions.view.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapterViewHolder<Data> : RecyclerView.ViewHolder {
    constructor(itemView: View?) : super(itemView!!)

    protected val context: Context
        get() = itemView.context

    abstract fun bindViews(data: Data)
}