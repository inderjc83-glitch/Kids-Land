package com.example.model

enum class AgeGroup(
    val title: String,
    val ageLabel: String,
    val subtitle: String,
    val avatarEmoji: String,
    val colorHex: Long,
    val description: String,
    val countingMax: Int,
    val mathDifficulty: String
) {
    TODDLER(
        title = "Little Explorers",
        ageLabel = "Age 2 - 4",
        subtitle = "Colors, Animals, 1-10 & Tracing",
        avatarEmoji = "🐣",
        colorHex = 0xFFFFB300,
        description = "Big buttons, gentle voice sounds, friendly shapes and animal sounds.",
        countingMax = 10,
        mathDifficulty = "Basic 1-5 counting"
    ),
    PRESCHOOL(
        title = "Smart Cubs",
        ageLabel = "Age 5 - 6",
        subtitle = "ABC Tracing, 1-50, Simple Math & Stories",
        avatarEmoji = "🦊",
        colorHex = 0xFF039BE5,
        description = "Alphabet handwriting, fruit counting, addition puzzles, rhymes and stories.",
        countingMax = 50,
        mathDifficulty = "Addition up to 10"
    ),
    PRIMARY(
        title = "Super Stars",
        ageLabel = "Age 7 - 9",
        subtitle = "1-100 Mastery, Math Puzzles & Cognitive Logic",
        avatarEmoji = "🚀",
        colorHex = 0xFF8E24AA,
        description = "Complete 1-100 number grid, mental math, spelling, and reading comprehension.",
        countingMax = 100,
        mathDifficulty = "Add & Subtract up to 20"
    );

    companion object {
        fun fromString(value: String): AgeGroup {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: PRESCHOOL
        }
    }
}
