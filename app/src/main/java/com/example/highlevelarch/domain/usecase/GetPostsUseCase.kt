package com.example.highlevelarch.domain.usecase

import com.example.highlevelarch.domain.model.Post
import com.example.highlevelarch.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * USE CASE (Interactor)
 * Encapsulates the business logic of retrieving and optionally filtering posts.
 */
class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(query: String = ""): Flow<Result<List<Post>>> {
        return repository.getPosts().map { result ->
            result.map { posts ->
                if (query.isBlank()) {
                    posts
                } else {
                    posts.filter { it.title.contains(query, ignoreCase = true) }
                }
            }
        }
    }
}
