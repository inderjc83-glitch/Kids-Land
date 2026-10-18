package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.TTSHelper
import com.example.data.EducationalContentRegistry
import com.example.data.KidsDatabase
import com.example.data.KidsRepository
import com.example.data.entity.ChildProfileEntity
import com.example.model.AgeGroup
import com.example.model.AnimalDrawing
import com.example.model.AppScreen
import com.example.model.MathProblem
import com.example.model.MemoryCard
import com.example.model.TracingGuide
import com.example.model.VocabCategory
import com.example.model.VocabItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class KidsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = KidsDatabase.getInstance(application)
    private val repository = KidsRepository(db.childProfileDao(), db.activityLogDao())
    val tts = TTSHelper(application)

    val profileState: StateFlow<ChildProfileEntity?> = repository.profileFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val recentLogs = repository.recentLogsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation & Global UI
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedAgeGroup = MutableStateFlow(AgeGroup.PRESCHOOL)
    val selectedAgeGroup: StateFlow<AgeGroup> = _selectedAgeGroup.asStateFlow()

    // Default to Playful Midnight Dark Mode as requested
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _showAgeDialog = MutableStateFlow(false)
    val showAgeDialog: StateFlow<Boolean> = _showAgeDialog.asStateFlow()

    private val _showParentLock = MutableStateFlow(false)
    val showParentLock: StateFlow<Boolean> = _showParentLock.asStateFlow()

    private val _showConfetti = MutableStateFlow(false)
    val showConfetti: StateFlow<Boolean> = _showConfetti.asStateFlow()

    // Vocabulary Section
    private val _activeVocabCategory = MutableStateFlow(VocabCategory.COLORS)
    val activeVocabCategory: StateFlow<VocabCategory> = _activeVocabCategory.asStateFlow()

    private val _activeVocabIndex = MutableStateFlow(0)
    val activeVocabIndex: StateFlow<Int> = _activeVocabIndex.asStateFlow()

    private val _currentlySpeakingId = MutableStateFlow<String?>(null)
    val currentlySpeakingId: StateFlow<String?> = _currentlySpeakingId.asStateFlow()

    // Math Section
    private val _mathSubTab = MutableStateFlow(0) // 0: Counting, 1: 1-100 Chart, 2: Math Balloons
    val mathSubTab: StateFlow<Int> = _mathSubTab.asStateFlow()

    private val _countingTarget = MutableStateFlow(5)
    val countingTarget: StateFlow<Int> = _countingTarget.asStateFlow()

    private val _countingCurrent = MutableStateFlow(0)
    val countingCurrent: StateFlow<Int> = _countingCurrent.asStateFlow()

    private val _currentMathProblem = MutableStateFlow(generateMathProblem(AgeGroup.PRESCHOOL))
    val currentMathProblem: StateFlow<MathProblem> = _currentMathProblem.asStateFlow()

    private val _mathFeedback = MutableStateFlow<String?>(null)
    val mathFeedback: StateFlow<String?> = _mathFeedback.asStateFlow()

    // Tracing Section
    private val _isAlphabetMode = MutableStateFlow(true)
    val isAlphabetMode: StateFlow<Boolean> = _isAlphabetMode.asStateFlow()

    private val _currentTracingIndex = MutableStateFlow(0)
    val currentTracingIndex: StateFlow<Int> = _currentTracingIndex.asStateFlow()

    // Coloring Section
    private val _currentDrawingIndex = MutableStateFlow(0)
    val currentDrawingIndex: StateFlow<Int> = _currentDrawingIndex.asStateFlow()

    private val _selectedColorHex = MutableStateFlow(0xFFFFB300)
    val selectedColorHex: StateFlow<Long> = _selectedColorHex.asStateFlow()

    private val _coloredZones = MutableStateFlow<Map<String, Long>>(emptyMap())
    val coloredZones: StateFlow<Map<String, Long>> = _coloredZones.asStateFlow()

    // Memory Match Game
    private val _memoryCards = MutableStateFlow<List<MemoryCard>>(emptyList())
    val memoryCards: StateFlow<List<MemoryCard>> = _memoryCards.asStateFlow()

    private val _memoryFlips = MutableStateFlow(0)
    val memoryFlips: StateFlow<Int> = _memoryFlips.asStateFlow()

    private val _memoryMatches = MutableStateFlow(0)
    val memoryMatches: StateFlow<Int> = _memoryMatches.asStateFlow()

    private val _memoryGameOver = MutableStateFlow(false)
    val memoryGameOver: StateFlow<Boolean> = _memoryGameOver.asStateFlow()

    private var firstFlippedCardIndex: Int? = null

    // Parent Dashboard
    private val _assessmentReportText = MutableStateFlow("")
    val assessmentReportText: StateFlow<String> = _assessmentReportText.asStateFlow()

    private val _parentReminderEnabled = MutableStateFlow(true)
    val parentReminderEnabled: StateFlow<Boolean> = _parentReminderEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureProfileExists()
            repository.profileFlow.collect { profile ->
                if (profile != null) {
                    _selectedAgeGroup.value = AgeGroup.fromString(profile.ageGroup)
                    _isDarkMode.value = profile.isDarkMode
                }
            }
        }
        initMemoryGame()
    }

    // --- Navigation & Core UI Actions ---

    fun navigateTo(screen: AppScreen) {
        tts.stop()
        if (screen == AppScreen.PARENT_DASHBOARD) {
            _showParentLock.value = true
        } else {
            _currentScreen.value = screen
        }
    }

    fun dismissParentLock() {
        _showParentLock.value = false
    }

    fun onParentLockSuccess() {
        _showParentLock.value = false
        _currentScreen.value = AppScreen.PARENT_DASHBOARD
        loadAssessmentReport()
    }

    fun openAgeSelection() {
        _showAgeDialog.value = true
    }

    fun closeAgeSelection() {
        _showAgeDialog.value = false
    }

    fun selectAgeGroup(age: AgeGroup) {
        _selectedAgeGroup.value = age
        _showAgeDialog.value = false
        _countingTarget.value = when (age) {
            AgeGroup.TODDLER -> 5
            AgeGroup.PRESCHOOL -> 10
            AgeGroup.PRIMARY -> 20
        }
        _currentMathProblem.value = generateMathProblem(age)
        viewModelScope.launch {
            repository.updateAgeGroup(age.name)
        }
        speak("Switched to ${age.title} mode!")
    }

    fun toggleDarkMode() {
        val next = !_isDarkMode.value
        _isDarkMode.value = next
        viewModelScope.launch {
            repository.updateTheme(next)
        }
    }

    fun triggerConfetti() {
        viewModelScope.launch {
            _showConfetti.value = true
            delay(2400)
            _showConfetti.value = false
        }
    }

    fun speak(text: String) {
        tts.speak(text)
    }

    // --- Vocabulary Section Actions ---

    fun setVocabCategory(category: VocabCategory) {
        _activeVocabCategory.value = category
        _activeVocabIndex.value = 0
        val items = getItemsForCategory(category)
        speak("${category.title}. ${items.size} items to explore.")
    }

    fun getItemsForCategory(category: VocabCategory): List<VocabItem> {
        return when (category) {
            VocabCategory.COLORS -> EducationalContentRegistry.colorsList
            VocabCategory.ANIMALS -> EducationalContentRegistry.animalsList
            VocabCategory.BIRDS -> EducationalContentRegistry.birdsList
            VocabCategory.MONTHS -> EducationalContentRegistry.monthsList
            VocabCategory.DAYS -> EducationalContentRegistry.daysList
        }
    }

    fun onSelectVocabIndex(index: Int) {
        _activeVocabIndex.value = index
    }

    fun nextVocabItem() {
        val items = getItemsForCategory(_activeVocabCategory.value)
        if (items.isNotEmpty()) {
            val nextIndex = (_activeVocabIndex.value + 1) % items.size
            _activeVocabIndex.value = nextIndex
            speakVocabItem(items[nextIndex])
        }
    }

    fun prevVocabItem() {
        val items = getItemsForCategory(_activeVocabCategory.value)
        if (items.isNotEmpty()) {
            val prevIndex = if (_activeVocabIndex.value > 0) _activeVocabIndex.value - 1 else items.size - 1
            _activeVocabIndex.value = prevIndex
            speakVocabItem(items[prevIndex])
        }
    }

    fun speakVocabItem(item: VocabItem) {
        _currentlySpeakingId.value = item.id
        tts.speakDetailItem(
            name = item.name,
            description = item.phoneticHint,
            funFact = item.funFact,
            onDone = {
                _currentlySpeakingId.value = null
            }
        )
        viewModelScope.launch {
            repository.recordActivity("VOCABULARY", "Learned ${item.name} (${item.category.title})")
        }
    }

    fun speakAllInCurrentCategory() {
        val items = getItemsForCategory(_activeVocabCategory.value)
        val names = items.joinToString(", ") { it.name }
        speak("Here are all ${_activeVocabCategory.value.title}: $names")
    }

    // --- Math Section Actions ---

    fun setMathSubTab(tabIndex: Int) {
        _mathSubTab.value = tabIndex
        when (tabIndex) {
            0 -> speak("Counting activity! Tap the fruits.")
            1 -> speak("Number chart from 1 to 100. Tap any number to hear it.")
            2 -> speak("Math balloon quiz! Pick the right number.")
        }
    }

    fun tapCountFruit() {
        if (_countingCurrent.value < _countingTarget.value) {
            val next = _countingCurrent.value + 1
            _countingCurrent.value = next
            speak("$next")

            if (next == _countingTarget.value) {
                triggerConfetti()
                speak("Wonderful! You counted all $next fruits!")
                viewModelScope.launch {
                    repository.awardStars(5, "MATH", "Counted to $next fruits successfully")
                }
            }
        }
    }

    fun resetCounting() {
        _countingCurrent.value = 0
        speak("Let's count again from 1!")
    }

    fun onMathOptionSelected(chosenAnswer: Int) {
        val problem = _currentMathProblem.value
        if (chosenAnswer == problem.correctAnswer) {
            _mathFeedback.value = "Great job! ${problem.num1} ${problem.operator} ${problem.num2} = ${problem.correctAnswer}"
            speak("Correct! You are awesome!")
            triggerConfetti()
            viewModelScope.launch {
                repository.awardStars(5, "MATH", "Solved ${problem.num1} ${problem.operator} ${problem.num2}")
            }
            viewModelScope.launch {
                delay(1800)
                _mathFeedback.value = null
                _currentMathProblem.value = generateMathProblem(_selectedAgeGroup.value)
            }
        } else {
            _mathFeedback.value = "Oops, try again! You can do it!"
            speak("Try again! Count the ${problem.itemName} carefully.")
        }
    }

    private fun generateMathProblem(age: AgeGroup): MathProblem {
        val (n1, n2, op, ans) = when (age) {
            AgeGroup.TODDLER -> {
                val a = Random.nextInt(1, 4)
                val b = Random.nextInt(1, 4)
                listOf(a, b, "+", a + b)
            }
            AgeGroup.PRESCHOOL -> {
                val a = Random.nextInt(2, 6)
                val b = Random.nextInt(1, 5)
                val isAdd = Random.nextBoolean()
                if (isAdd) listOf(a, b, "+", a + b) else listOf(a + b, b, "-", a)
            }
            AgeGroup.PRIMARY -> {
                val opType = Random.nextInt(3)
                when (opType) {
                    0 -> {
                        val a = Random.nextInt(5, 12)
                        val b = Random.nextInt(3, 9)
                        listOf(a, b, "+", a + b)
                    }
                    1 -> {
                        val b = Random.nextInt(2, 8)
                        val a = b + Random.nextInt(2, 8)
                        listOf(a, b, "-", a - b)
                    }
                    else -> {
                        val a = Random.nextInt(2, 5)
                        val b = Random.nextInt(2, 5)
                        listOf(a, b, "×", a * b)
                    }
                }
            }
        }
        val correct = ans as Int
        val num1 = n1 as Int
        val num2 = n2 as Int
        val operator = op as String

        val distractor1 = correct + 1
        val distractor2 = if (correct > 2) correct - 1 else correct + 2
        val options = listOf(correct, distractor1, distractor2).shuffled()

        return MathProblem(
            id = "math_${System.currentTimeMillis()}",
            num1 = num1,
            num2 = num2,
            operator = operator,
            correctAnswer = correct,
            options = options,
            itemEmoji = "🍎",
            itemName = "apples",
            visualHint = "$num1 apples $operator $num2 apples"
        )
    }

    // --- Tracing Section Actions ---

    fun setTracingMode(isAlpha: Boolean) {
        _isAlphabetMode.value = isAlpha
        _currentTracingIndex.value = 0
        val guide = currentTracingGuide()
        speak("${guide.char}! ${guide.char} is for ${guide.hintWord}")
    }

    fun currentTracingGuide(): TracingGuide {
        val list = if (_isAlphabetMode.value) {
            EducationalContentRegistry.alphabetTracingList
        } else {
            EducationalContentRegistry.numberTracingList
        }
        return list[_currentTracingIndex.value.coerceIn(0, list.size - 1)]
    }

    fun nextTracingChar() {
        val list = if (_isAlphabetMode.value) {
            EducationalContentRegistry.alphabetTracingList
        } else {
            EducationalContentRegistry.numberTracingList
        }
        _currentTracingIndex.value = (_currentTracingIndex.value + 1) % list.size
        val guide = currentTracingGuide()
        speak("${guide.char}! ${guide.char} is for ${guide.hintWord}")
    }

    fun prevTracingChar() {
        val list = if (_isAlphabetMode.value) {
            EducationalContentRegistry.alphabetTracingList
        } else {
            EducationalContentRegistry.numberTracingList
        }
        _currentTracingIndex.value = if (_currentTracingIndex.value > 0) _currentTracingIndex.value - 1 else list.size - 1
        val guide = currentTracingGuide()
        speak("${guide.char}! ${guide.char} is for ${guide.hintWord}")
    }

    fun completeTracingStroke() {
        val guide = currentTracingGuide()
        triggerConfetti()
        speak("Super handwriting! You traced ${guide.char} perfectly!")
        viewModelScope.launch {
            repository.awardStars(5, "TRACING", "Traced ${guide.char} (${guide.hintWord})")
        }
    }

    // --- Coloring Section Actions ---

    fun selectDrawing(index: Int) {
        _currentDrawingIndex.value = index
        _coloredZones.value = emptyMap()
        val drawing = EducationalContentRegistry.animalDrawings[index]
        speak("${drawing.name}! ${drawing.funFact}")
    }

    fun selectColorHex(hex: Long) {
        _selectedColorHex.value = hex
    }

    fun colorZone(zoneId: String) {
        val updated = _coloredZones.value.toMutableMap()
        updated[zoneId] = _selectedColorHex.value
        _coloredZones.value = updated

        val drawing = EducationalContentRegistry.animalDrawings[_currentDrawingIndex.value]
        if (updated.size == drawing.defaultSvgShapes.size) {
            triggerConfetti()
            speak("Magnificent artwork! You finished coloring ${drawing.name}!")
            viewModelScope.launch {
                repository.awardStars(8, "COLORING", "Completed coloring ${drawing.name}")
            }
        }
    }

    fun resetDrawing() {
        _coloredZones.value = emptyMap()
        speak("Blank canvas ready! Let's color again.")
    }

    // --- Memory Match Game Actions ---

    fun initMemoryGame() {
        val rawItems = listOf(
            Pair("🐶", "Puppy"),
            Pair("🐱", "Kitty"),
            Pair("🦁", "Lion"),
            Pair("🐘", "Elephant"),
            Pair("🍎", "Apple"),
            Pair("⭐", "Star")
        )
        val colors = listOf(0xFF29B6F6, 0xFFFF7043, 0xFF66BB6A, 0xFFAB47BC, 0xFFFFD54F, 0xFFF06292)

        val cardPairs = mutableListOf<MemoryCard>()
        var idCounter = 1
        rawItems.forEachIndexed { index, (emoji, title) ->
            val color = colors[index % colors.size]
            cardPairs.add(MemoryCard(id = idCounter++, pairKey = title, displayEmoji = emoji, title = title, colorHex = color))
            cardPairs.add(MemoryCard(id = idCounter++, pairKey = title, displayEmoji = emoji, title = title, colorHex = color))
        }

        _memoryCards.value = cardPairs.shuffled()
        _memoryFlips.value = 0
        _memoryMatches.value = 0
        _memoryGameOver.value = false
        firstFlippedCardIndex = null
    }

    fun onCardTapped(index: Int) {
        val cards = _memoryCards.value.toMutableList()
        val card = cards[index]

        if (card.isFlipped || card.isMatched) return

        // Flip this card
        cards[index] = card.copy(isFlipped = true)
        _memoryCards.value = cards
        _memoryFlips.value = _memoryFlips.value + 1
        speak(card.title)

        val firstIdx = firstFlippedCardIndex
        if (firstIdx == null) {
            firstFlippedCardIndex = index
        } else {
            firstFlippedCardIndex = null
            val firstCard = cards[firstIdx]
            if (firstCard.pairKey == card.pairKey) {
                // Match!
                cards[firstIdx] = firstCard.copy(isMatched = true)
                cards[index] = card.copy(isMatched = true)
                _memoryCards.value = cards
                val newMatches = _memoryMatches.value + 1
                _memoryMatches.value = newMatches
                speak("Match! ${card.title}")

                if (newMatches == cards.size / 2) {
                    _memoryGameOver.value = true
                    triggerConfetti()
                    speak("Fantastic memory! You matched all pairs in ${_memoryFlips.value} flips!")
                    viewModelScope.launch {
                        repository.awardStars(6, "MEMORY", "Memory Game Matched in ${_memoryFlips.value} flips")
                    }
                }
            } else {
                // Not match, flip back after brief pause
                viewModelScope.launch {
                    delay(900)
                    val resetCards = _memoryCards.value.toMutableList()
                    resetCards[firstIdx] = resetCards[firstIdx].copy(isFlipped = false)
                    resetCards[index] = resetCards[index].copy(isFlipped = false)
                    _memoryCards.value = resetCards
                }
            }
        }
    }

    // --- Parent Dashboard Actions ---

    fun loadAssessmentReport() {
        viewModelScope.launch {
            _assessmentReportText.value = repository.generateAssessmentReport()
        }
    }

    fun toggleReminder(enabled: Boolean) {
        _parentReminderEnabled.value = enabled
    }

    override fun onCleared() {
        super.onCleared()
        tts.shutdown()
    }
}
