package com.example.homeworkattractions.view.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homeworkattractions.data.APIRepository
import com.example.homeworkattractions.model.NewsItem
import com.example.homeworkattractions.util.LocaleHelper.getCurrentLocale
import kotlin.math.max

class NewsPagingSource(
    private val repository: APIRepository
) : PagingSource<Int, NewsItem>() {
    private fun ensureValidKey(key: Int) = max(1, key)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val page = params.key ?: 1
        return try {
            val lang = getCurrentLocale()
            val response = repository.fetchNews(lang, page)
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val news = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = news.id - (state.config.pageSize / 2))
    }
}
