package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.ChildProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildProfileDao {
    @Query("SELECT * FROM child_profile WHERE id = 1 LIMIT 1")
    fun getProfileFlow(): Flow<ChildProfileEntity?>

    @Query("SELECT * FROM child_profile WHERE id = 1 LIMIT 1")
    suspend fun getProfile(): ChildProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: ChildProfileEntity)

    @Update
    suspend fun update(profile: ChildProfileEntity)

    @Query("UPDATE child_profile SET ageGroup = :newAgeGroup WHERE id = 1")
    suspend fun updateAgeGroup(newAgeGroup: String)

    @Query("UPDATE child_profile SET totalStars = totalStars + :starsToAdd WHERE id = 1")
    suspend fun addStars(starsToAdd: Int)

    @Query("UPDATE child_profile SET currentStreak = :streak, lastActiveDate = :todayDate WHERE id = 1")
    suspend fun updateStreak(streak: Int, todayDate: String)

    @Query("UPDATE child_profile SET studyMinutesToday = studyMinutesToday + :minutes WHERE id = 1")
    suspend fun addStudyMinutes(minutes: Int)

    @Query("UPDATE child_profile SET isDarkMode = :darkMode WHERE id = 1")
    suspend fun updateDarkMode(darkMode: Boolean)
}
