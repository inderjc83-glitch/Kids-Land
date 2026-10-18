package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "child_profile")
data class ChildProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Junior Explorer",
    val ageGroup: String = "PRESCHOOL",
    val totalStars: Int = 15,
    val currentStreak: Int = 1,
    val lastActiveDate: String = "",
    val unlockedStickers: String = "star,apple,trophy",
    val studyMinutesToday: Int = 12,
    val isDarkMode: Boolean = true,
    val dailyGoalMinutes: Int = 20
)
