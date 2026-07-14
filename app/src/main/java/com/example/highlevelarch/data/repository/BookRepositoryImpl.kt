package com.example.highlevelarch.data.repository

import com.example.highlevelarch.data.mapper.toDomain
import com.example.highlevelarch.data.remote.SearchResponse
import com.example.highlevelarch.domain.model.Book
import com.example.highlevelarch.domain.repository.BookRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BookRepositoryImpl(private val client: HttpClient) : BookRepository {
    override suspend fun searchBooks(query: String): List<Book> {
        if (query.isBlank()) return emptyList()
        val response: SearchResponse = client.get("https://openlibrary.org/search.json") {
            url {
                parameters.append("q", query)
            }
        }.body()
        return response.docs.map { it.toDomain() }
    }
}
