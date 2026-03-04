package com.example.highlevelarch.data.remote

import retrofit2.http.GET

/**
 * RETROFIT API SERVICE
 * Connects to JSONPlaceholder (/posts).
 */
interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}

/**
 * DATA TRANSFER OBJECT (DTO)
 * Matches the JSON structure from the API.
 */
data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
