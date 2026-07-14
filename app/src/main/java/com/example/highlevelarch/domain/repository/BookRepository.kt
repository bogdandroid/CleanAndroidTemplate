package com.example.highlevelarch.domain.repository

import com.example.highlevelarch.domain.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String): List<Book>
}
