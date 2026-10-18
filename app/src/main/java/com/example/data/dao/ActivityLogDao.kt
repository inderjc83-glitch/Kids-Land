package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.entity.ActivityLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityLogDao {
    @Insert
    suspend fun logActivity(log: ActivityLogEntity)

    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 30")
    fun getRecentLogsFlow(): Flow<List<ActivityLogEntity>>

    @Query("SELECT COUNT(*) FROM activity_logs WHERE category = :category")
    suspend fun getCountByCategory(category: String): Int

    @Query("SELECT COUNT(*) FROM activity_logs")
    suspend fun getTotalCompletedLessons(): Int

    @Query("SELECT COALESCE(SUM(starsEarned), 0) FROM activity_logs")
    suspend fun getTotalStarsEarned(): Int
}
