package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String, // "VOCAB", "MATH", "TRACING", "COLORING", "MEMORY", "STORY"
    val activityTitle: String,
    val starsEarned: Int,
    val score: Int = 100,
    val timestamp: Long = System.currentTimeMillis()
)
