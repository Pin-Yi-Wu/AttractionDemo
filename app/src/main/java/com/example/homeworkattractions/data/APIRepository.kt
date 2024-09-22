package com.example.homeworkattractions.data

import com.example.homeworkattractions.model.AttractionResponse
import com.example.homeworkattractions.model.NewsResponse
import javax.inject.Inject

class APIRepository @Inject constructor(private val apiRemoteDataSource: ApiRemoteDataSource) {

    suspend fun testApiCall() {
        return apiRemoteDataSource.testApiCall()
    }

    suspend fun fetchNews(lang: String, page: Int): NewsResponse {
        return apiRemoteDataSource.getNews(lang, page)
    }

    suspend fun fetchAttractions(lang: String, page: Int): AttractionResponse {
        return apiRemoteDataSource.getAttractions(lang, page)
    }
}
