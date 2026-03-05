package com.example.highlevelarch.feature.search

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
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

class SearchRepository(private val client: HttpClient) {
    suspend fun searchBooks(query: String): List<BookDto> {
        if (query.isBlank()) return emptyList()
        val response: SearchResponse = client.get("https://openlibrary.org/search.json") {
            url {
                parameters.append("q", query)
            }
        }.body()
        return response.docs
    }
}
