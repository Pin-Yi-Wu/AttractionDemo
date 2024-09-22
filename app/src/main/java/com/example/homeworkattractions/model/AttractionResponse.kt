package com.example.homeworkattractions.model

import java.io.Serializable

data class AttractionResponse(
    val total: Int,
    val data: List<Attraction>
): Serializable

