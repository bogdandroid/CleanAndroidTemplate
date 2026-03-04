package com.example.highlevelarch.data.repository

import com.example.highlevelarch.data.local.PostDao
import com.example.highlevelarch.data.mapper.toDomain
import com.example.highlevelarch.data.mapper.toEntity
import com.example.highlevelarch.data.remote.PostApiService
import com.example.highlevelarch.domain.model.Post
import com.example.highlevelarch.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * REPOSITORY IMPLEMENTATION
 * Implements the Domain interface. Handles the "Offline-First" Single Source of Truth strategy.
 */
class PostRepositoryImpl @Inject constructor(
    private val api: PostApiService,
    private val dao: PostDao
) : PostRepository {

    override fun getPosts(): Flow<Result<List<Post>>> = flow {
        // Step 1: Emit local cache immediately
        val local = dao.getAllPosts().first()
        if (local.isNotEmpty()) {
            emit(Result.success(local.map { it.toDomain() }))
        }

        // Step 2: Try updating from API
        try {
            val networkPosts = api.getPosts()
            val entities = networkPosts.map { it.toEntity() }
            
            // Step 3: Update SSOT
            dao.clearAll()
            dao.insertPosts(entities)
            
            // Step 4: Emit fresh data mapped to Domain model
            emit(Result.success(dao.getAllPosts().first().map { it.toDomain() }))

        } catch (e: Exception) {
            // If network fails and cache is empty, emit error
            if (local.isEmpty()) {
                emit(Result.failure(e))
            }
        }
    }
}
