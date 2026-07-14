package com.example.highlevelarch.data.mapper

import com.example.highlevelarch.data.remote.BookDto
import com.example.highlevelarch.domain.model.Book

fun BookDto.toDomain(): Book {
    return Book(
        title = this.title,
        authors = this.authorName ?: emptyList()
    )
}
