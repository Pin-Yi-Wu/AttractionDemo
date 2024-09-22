package com.example.homeworkattractions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkattractions.data.APIRepository
import com.example.homeworkattractions.model.Attraction
import com.example.homeworkattractions.model.NewsItem
import com.example.homeworkattractions.util.LocaleHelper.getCurrentLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: APIRepository) : ViewModel() {
    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _newsLiveData = MutableLiveData<List<NewsItem>?>()
    val newsLiveData: LiveData<List<NewsItem>?> = _newsLiveData

    private val _attractionsLiveData = MutableLiveData<List<Attraction>?>()
    val attractionsLiveData: LiveData<List<Attraction>?> = _attractionsLiveData

    fun fetchHomeData() {
        _isViewLoading.value = true
        viewModelScope.launch {
            _newsLiveData.value = null
            _attractionsLiveData.value = null

            val lang = getCurrentLocale()
            try {
                val newsResponse = repository.fetchNews(lang, 1)
                _newsLiveData.value = newsResponse.data
            } catch (e: Exception) {
                _newsLiveData.value = null
                _attractionsLiveData.value = null
            }

            try {
                val attractionResponse = repository.fetchAttractions(lang, 1)
                _attractionsLiveData.value = attractionResponse.data
            } catch (e: Exception) {
                _newsLiveData.value = null
                _attractionsLiveData.value = null
            } finally {
                _isViewLoading.value = false
            }
        }
    }
}