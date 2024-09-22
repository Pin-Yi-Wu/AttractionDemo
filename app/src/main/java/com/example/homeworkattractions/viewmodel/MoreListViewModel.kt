package com.example.homeworkattractions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homeworkattractions.data.APIRepository
import com.example.homeworkattractions.model.Attraction
import com.example.homeworkattractions.model.NewsItem
import com.example.homeworkattractions.view.pagingsource.AttractionsPagingSource
import com.example.homeworkattractions.view.pagingsource.NewsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoreListViewModel @Inject constructor(private val repository: APIRepository) : ViewModel() {
    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private var _pagedNews: Flow<PagingData<NewsItem>>? = null
    val pagedNews: Flow<PagingData<NewsItem>>?
        get() = _pagedNews

    private var _pagedAttractions: Flow<PagingData<Attraction>>? = null
    val pagedAttractions: Flow<PagingData<Attraction>>?
        get() = _pagedAttractions

    fun fetchPagedNews() {
        _pagedNews = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { NewsPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun fetchPagedAttractions() {
        _pagedAttractions = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { AttractionsPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }
}