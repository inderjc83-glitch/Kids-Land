package com.example.data

import com.example.data.dao.ActivityLogDao
import com.example.data.dao.ChildProfileDao
import com.example.data.entity.ActivityLogEntity
import com.example.data.entity.ChildProfileEntity
import com.example.model.AgeGroup
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class KidsRepository(
    private val profileDao: ChildProfileDao,
    private val activityLogDao: ActivityLogDao
) {
    val profileFlow: Flow<ChildProfileEntity?> = profileDao.getProfileFlow()
    val recentLogsFlow: Flow<List<ActivityLogEntity>> = activityLogDao.getRecentLogsFlow()

    suspend fun ensureProfileExists() {
        val existing = profileDao.getProfile()
        if (existing == null) {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            profileDao.insertOrUpdate(
                ChildProfileEntity(
                    id = 1,
                    name = "Young Scholar",
                    ageGroup = AgeGroup.PRESCHOOL.name,
                    totalStars = 25,
                    currentStreak = 1,
                    lastActiveDate = today,
                    unlockedStickers = "s1,s2,s3",
                    studyMinutesToday = 15,
                    isDarkMode = true
                )
            )
        } else {
            // Check streak
            checkAndUpdateDailyStreak(existing)
        }
    }

    private suspend fun checkAndUpdateDailyStreak(profile: ChildProfileEntity) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        if (profile.lastActiveDate != today) {
            // If active yesterday, streak increases, else keeps or starts at 1
            val newStreak = if (profile.lastActiveDate.isNotEmpty()) {
                val lastDate = try {
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(profile.lastActiveDate)
                } catch (e: Exception) { null }

                if (lastDate != null) {
                    val diffDays = (Date().time - lastDate.time) / (1000 * 60 * 60 * 24)
                    if (diffDays <= 1) profile.currentStreak + 1 else 1
                } else 1
            } else 1

            profileDao.updateStreak(newStreak, today)
        }
    }

    suspend fun setAgeGroup(ageGroup: AgeGroup) {
        profileDao.updateAgeGroup(ageGroup.name)
    }

    suspend fun updateAgeGroup(ageGroupName: String) {
        profileDao.updateAgeGroup(ageGroupName)
    }

    suspend fun awardStars(stars: Int, category: String, activityTitle: String) {
        profileDao.addStars(stars)
        activityLogDao.logActivity(
            ActivityLogEntity(
                category = category,
                activityTitle = activityTitle,
                starsEarned = stars,
                score = 100
            )
        )
        // Add 2 study minutes per completed exercise
        profileDao.addStudyMinutes(2)
    }

    suspend fun recordActivity(category: String, activityTitle: String) {
        activityLogDao.logActivity(
            ActivityLogEntity(
                category = category,
                activityTitle = activityTitle,
                starsEarned = 1,
                score = 100
            )
        )
    }

    suspend fun toggleDarkMode(isDark: Boolean) {
        profileDao.updateDarkMode(isDark)
    }

    suspend fun updateTheme(isDark: Boolean) {
        profileDao.updateDarkMode(isDark)
    }

    suspend fun generateAssessmentReport(): String {
        val profile = profileDao.getProfile() ?: ChildProfileEntity()
        val totalLessons = activityLogDao.getTotalCompletedLessons()
        val totalStars = profile.totalStars
        val streak = profile.currentStreak
        val minutes = profile.studyMinutesToday
        val ageEnum = AgeGroup.fromString(profile.ageGroup)

        val vocabCount = activityLogDao.getCountByCategory("VOCAB")
        val mathCount = activityLogDao.getCountByCategory("MATH")
        val tracingCount = activityLogDao.getCountByCategory("TRACING")
        val coloringCount = activityLogDao.getCountByCategory("COLORING")
        val storyCount = activityLogDao.getCountByCategory("STORY")

        val todayDate = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault()).format(Date())

        return buildString {
            appendLine("═══════════════════════════════════════")
            appendLine("🌟 KIDSLAND ACADEMY - PROGRESS REPORT 🌟")
            appendLine("═══════════════════════════════════════")
            appendLine("Learner Name     : ${profile.name}")
            appendLine("Learning Tier    : ${ageEnum.title} (${ageEnum.ageLabel})")
            appendLine("Report Generated : $todayDate")
            appendLine("Status           : Active (Offline Verified)")
            appendLine("───────────────────────────────────────")
            appendLine("📊 LEARNING METRICS:")
            appendLine(" • Total Stars Earned    : $totalStars ⭐")
            appendLine(" • Current Study Streak  : $streak Days 🔥")
            appendLine(" • Today's Learning Time : $minutes Minutes ⏱️")
            appendLine(" • Total Completed Tasks : $totalLessons Modules 🎯")
            appendLine("───────────────────────────────────────")
            appendLine("📚 SUBJECT BREAKDOWN:")
            appendLine(" • Vocabulary & Phonics  : $vocabCount activities completed")
            appendLine(" • Math & 1-100 Counting : $mathCount challenges solved")
            appendLine(" • Alphabet Handwriting  : $tracingCount letters/numbers traced")
            appendLine(" • Creative Animal Art   : $coloringCount artworks finished")
            appendLine(" • Stories & Rhymes      : $storyCount stories explored")
            appendLine("───────────────────────────────────────")
            appendLine("💡 EDUCATIONAL RECOMMENDATION:")
            appendLine(
                when (ageEnum) {
                    AgeGroup.TODDLER -> "Excellent sensory engagement! Encourage repetitive listening to Animal and Bird sounds, and practice drawing smooth single strokes."
                    AgeGroup.PRESCHOOL -> "Great phonics & handwriting momentum! Continue daily alphabet tracing and simple fruit-counting addition puzzles."
                    AgeGroup.PRIMARY -> "Outstanding cognitive mastery! Challenge with the 1-100 number grid and reading comprehension questions."
                }
            )
            appendLine("═══════════════════════════════════════")
            appendLine("Printed via KidsLand Academy Parent Zone")
        }
    }
}
