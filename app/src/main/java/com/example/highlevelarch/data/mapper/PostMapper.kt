package com.example.highlevelarch.data.mapper

import com.example.highlevelarch.data.local.PostEntity
import com.example.highlevelarch.data.remote.PostDto
import com.example.highlevelarch.domain.model.Post

/**
 * DATA MAPPERS
 * Isolate the framework-specific models (Retrofit/Room) from the Domain layer.
 */
fun PostDto.toEntity(): PostEntity {
    return PostEntity(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body
    )
}

fun PostEntity.toDomain(): Post {
    return Post(
        id = this.id,
        title = this.title,
        body = this.body
    )
}
