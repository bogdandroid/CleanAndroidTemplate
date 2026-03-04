package com.example.highlevelarch.domain.repository

import com.example.highlevelarch.domain.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * REPOSITORY INTERFACE (Dependency Inversion)
 * The Domain layer defines what it needs; the Data layer implements it.
 */
interface PostRepository {
    fun getPosts(): Flow<Result<List<Post>>>
}
