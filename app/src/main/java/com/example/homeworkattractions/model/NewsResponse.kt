package com.example.homeworkattractions.model

import java.io.Serializable

data class NewsResponse(
    val total: Int,
    val data: List<NewsItem>
): Serializable

data class NewsItem(
    val id: Int,
    val title: String,
    val description: String,
    val posted: String,
    val url: String,
    val files: List<File>,
    val links: List<NewsLink>
): Serializable

data class NewsLink(
    val src: String,
    val subject: String
): Serializable

data class File(
    val src: String,
    val subject: String,
    val ext: String,
): Serializable
