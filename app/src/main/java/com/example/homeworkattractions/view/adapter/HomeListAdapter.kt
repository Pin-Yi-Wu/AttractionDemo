package com.example.homeworkattractions.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.homeworkattractions.R
import com.example.homeworkattractions.databinding.LayerAttractionsBinding
import com.example.homeworkattractions.databinding.LayerNewsBinding
import com.example.homeworkattractions.databinding.LayerTitleBinding
import com.example.homeworkattractions.databinding.ViewEmptyLayoutBinding
import com.example.homeworkattractions.model.Attraction
import com.example.homeworkattractions.model.HomeItemList
import com.example.homeworkattractions.model.NewsItem
import com.example.homeworkattractions.view.viewholder.AttractionsViewHolder
import com.example.homeworkattractions.view.viewholder.BaseAdapterViewHolder
import com.example.homeworkattractions.view.viewholder.EmptyViewHolder
import com.example.homeworkattractions.view.viewholder.NewsViewHolder
import com.example.homeworkattractions.view.viewholder.TitleViewHolder

class HomeListAdapter(
    private val onItemClickListener: (viewType: Int, homeItem: HomeItemList, position: Int) -> Unit,
    private val onItemClickMoreListener: (viewType: Int?) -> Unit
) : BaseRecyclerViewAdapter<HomeItemList, BaseAdapterViewHolder<HomeItemList>>() {
    var homeItemList: MutableList<HomeItemList> = mutableListOf()

    fun showNewsList(context: Context, news: List<NewsItem>) {
        val newsItemListIndex = homeItemList.indexOfFirst { it is HomeItemList.NewsItemList }

        if (newsItemListIndex != -1) {
            homeItemList[newsItemListIndex] = HomeItemList.NewsItemList(news)
            notifyItemChanged(newsItemListIndex)
        } else {
            homeItemList.add(
                HomeItemList.HomeTitle(
                    context.getString(R.string.home_news_title),
                    DataType.NEWS
                )
            )
            homeItemList.add(HomeItemList.NewsItemList(news))
            notifyDataSetChanged()
        }
    }

    fun showAttractionsList(context: Context, attractions: List<Attraction>) {
        val newsItemIndex = homeItemList.indexOfFirst { it is HomeItemList.AttractionItemList }

        if (newsItemIndex != -1) {
            homeItemList[newsItemIndex] = HomeItemList.AttractionItemList(attractions)
            notifyItemChanged(newsItemIndex)
        } else {
            homeItemList.add(
                HomeItemList.HomeTitle(
                    context.getString(R.string.home_attractions_title),
                    DataType.ATTRACTIONS
                )
            )
            homeItemList.add(HomeItemList.AttractionItemList(attractions))
            notifyDataSetChanged()
        }
    }

    fun showEmptyView() {
        homeItemList.clear()
        homeItemList.add(HomeItemList.Empty(null))
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return homeItemList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseAdapterViewHolder<HomeItemList> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            DataType.TITLE -> {
                val binding = LayerTitleBinding.inflate(inflater, parent, false)
                TitleViewHolder(binding)
            }

            DataType.NEWS -> {
                val binding = LayerNewsBinding.inflate(inflater, parent, false)
                NewsViewHolder(binding)
            }

            DataType.ATTRACTIONS -> {
                val binding = LayerAttractionsBinding.inflate(inflater, parent, false)
                AttractionsViewHolder(binding)
            }

            else -> {
                val binding = ViewEmptyLayoutBinding.inflate(inflater, parent, false)
                EmptyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseAdapterViewHolder<HomeItemList>, position: Int) {
        holder.bindViews(homeItemList[position])
        var viewType = getItemViewType(position)
        when (viewType) {
            DataType.NEWS -> {
                if (holder is NewsViewHolder) {
                    holder.setOnItemClickListener { childPosition ->
                        onItemClickListener.invoke(viewType, homeItemList[position], childPosition)
                    }
                }
            }

            DataType.ATTRACTIONS -> {
                if (holder is AttractionsViewHolder) {
                    holder.setOnItemClickListener { childPosition ->
                        onItemClickListener.invoke(viewType, homeItemList[position], childPosition)
                    }
                }
            }

            DataType.TITLE -> {
                if (holder is TitleViewHolder) {
                    holder.setOnItemClickMoreListener {
                        onItemClickMoreListener.invoke(it)
                    }
                }
            }
        }
    }

    override fun getItemViewType(pos: Int): Int {
        return when (homeItemList[pos]) {
            is HomeItemList.NewsItemList -> DataType.NEWS
            is HomeItemList.AttractionItemList -> DataType.ATTRACTIONS
            is HomeItemList.HomeTitle -> DataType.TITLE
            else -> DataType.EMPTY
        }
    }

    object DataType {
        const val TITLE = 1
        const val NEWS = 2
        const val ATTRACTIONS = 3
        const val EMPTY = 0
    }
}
