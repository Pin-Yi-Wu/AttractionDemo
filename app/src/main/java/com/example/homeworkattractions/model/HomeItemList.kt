package com.example.homeworkattractions.model

sealed class HomeItemList {
    data class NewsItemList(val newsList: List<NewsItem>) : HomeItemList()
    data class AttractionItemList(val attractionList: List<Attraction>) : HomeItemList()
    data class HomeTitle(val title: String, val viewType: Int): HomeItemList()
    data class Empty(val lang: String?): HomeItemList()
}
