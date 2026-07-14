package com.example.highlevelarch.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val docs: List<BookDto>
)

@Serializable
data class BookDto(
    val title: String,
    @SerialName("author_name") val authorName: List<String>? = null
)
