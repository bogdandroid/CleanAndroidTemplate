package com.example.highlevelarch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * DATABASE ENTITY
 * This is our Single Source of Truth (SSOT).
 */
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
