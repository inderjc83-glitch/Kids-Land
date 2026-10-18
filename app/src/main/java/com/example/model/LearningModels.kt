package com.example.model

enum class AppScreen(val label: String, val iconEmoji: String) {
    HOME("Home", "🏠"),
    VOCAB_MENU("Topics Menu", "🗂️"),
    VOCABULARY("Learn Words", "🗣️"),
    MATH("Math & Numbers", "🔢"),
    TRACING("ABC Tracing", "✍️"),
    COLORING("Animal Colors", "🎨"),
    MEMORY("Memory & Shapes", "🧩"),
    REWARDS("Rewards & Badges", "🏆"),
    PARENT_DASHBOARD("Parent Zone", "🛡️")
}

enum class VocabCategory(val title: String, val emoji: String, val subtitle: String) {
    COLORS("Colors", "🎨", "Learn Bright Colors"),
    ANIMALS("Animals", "🦁", "Wild & Pet Animals"),
    BIRDS("Birds", "🦜", "Flying Feather Friends"),
    MONTHS("Months", "📅", "12 Months of the Year"),
    DAYS("Weekdays", "☀️", "Monday to Sunday (7 Days)")
}

data class VocabItem(
    val id: String,
    val category: VocabCategory,
    val name: String,
    val phoneticHint: String,
    val emoji: String,
    val colorHex: Long,
    val funFact: String,
    val sampleSentence: String
)

data class MathProblem(
    val id: String,
    val num1: Int,
    val num2: Int,
    val operator: String, // "+", "-", "×"
    val correctAnswer: Int,
    val options: List<Int>,
    val itemEmoji: String,
    val itemName: String,
    val visualHint: String
)

data class TracingGuide(
    val char: String,
    val isNumber: Boolean = false,
    val hintWord: String,
    val hintEmoji: String,
    val strokeDescription: String
)

data class AnimalDrawing(
    val id: String,
    val name: String,
    val emoji: String,
    val habitat: String,
    val funFact: String,
    val defaultSvgShapes: List<ColorableZone>
)

data class ColorableZone(
    val zoneId: String,
    val label: String,
    val defaultColorHex: Long = 0xFFFFFFFF
)

data class MemoryCard(
    val id: Int,
    val pairKey: String,
    val displayEmoji: String,
    val title: String,
    val colorHex: Long,
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false
)

data class RewardBadge(
    val id: String,
    val title: String,
    val emoji: String,
    val description: String,
    val starsRequired: Int,
    val isUnlocked: Boolean = false
)

data class Sticker(
    val id: String,
    val name: String,
    val emoji: String,
    val starCost: Int,
    val isUnlocked: Boolean = false
)
