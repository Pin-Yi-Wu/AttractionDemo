package com.example.homeworkattractions.data

import com.example.homeworkattractions.model.AttractionResponse
import com.example.homeworkattractions.model.NewsResponse
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(private val apiClient: ApiClient) {

    suspend fun testApiCall() {
        return apiClient.testApiCall()
    }

    suspend fun getNews(lang: String, page: Int): NewsResponse {
        return apiClient.createService().fetchNews(lang, page)
    }

    suspend fun getAttractions(lang: String, page: Int): AttractionResponse {
        return apiClient.createService().fetchAttractions(lang, page)
    }
}