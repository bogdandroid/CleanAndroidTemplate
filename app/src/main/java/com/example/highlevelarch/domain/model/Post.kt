package com.example.highlevelarch.domain.model

/**
 * DOMAIN MODEL
 * A clean representation of a Post, completely decoupled from Room or Retrofit annotations.
 */
data class Post(
    val id: Int,
    val title: String,
    val body: String
)
