package com.example.highlevelarch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * ROOM DATABASE
 * Main entry point for local DB.
 */
@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
