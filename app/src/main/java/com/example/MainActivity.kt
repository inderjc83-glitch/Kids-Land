package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.EducationalContentRegistry
import com.example.model.AppScreen
import com.example.ui.components.AgeSelectionDialog
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.KidsTopBar
import com.example.ui.components.ParentLockDialog
import com.example.ui.screens.ColoringBookScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MathHubScreen
import com.example.ui.screens.MemoryMatchScreen
import com.example.ui.screens.ParentDashboardScreen
import com.example.ui.screens.StickersRewardsScreen
import com.example.ui.screens.TracingPadScreen
import com.example.ui.screens.VocabMenuScreen
import com.example.ui.screens.VocabularyScreen
import com.example.ui.theme.KidsLandTheme
import com.example.viewmodel.KidsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: KidsViewModel = viewModel()
            KidsLandApp(viewModel)
        }
    }
}

@Composable
fun KidsLandApp(viewModel: KidsViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val childProfile by viewModel.profileState.collectAsState()
    val ageGroup by viewModel.selectedAgeGroup.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val showAgeDialog by viewModel.showAgeDialog.collectAsState()
    val showParentLock by viewModel.showParentLock.collectAsState()
    val showConfetti by viewModel.showConfetti.collectAsState()

    // Vocabulary states
    val activeVocabCategory by viewModel.activeVocabCategory.collectAsState()
    val activeVocabIndex by viewModel.activeVocabIndex.collectAsState()
    val currentlySpeakingId by viewModel.currentlySpeakingId.collectAsState()
    val vocabItems = viewModel.getItemsForCategory(activeVocabCategory)

    // Math states
    val mathSubTab by viewModel.mathSubTab.collectAsState()
    val countingTarget by viewModel.countingTarget.collectAsState()
    val countingCurrent by viewModel.countingCurrent.collectAsState()
    val currentMathProblem by viewModel.currentMathProblem.collectAsState()
    val mathFeedback by viewModel.mathFeedback.collectAsState()

    // Tracing states
    val isAlphabetMode by viewModel.isAlphabetMode.collectAsState()
    val currentTracingGuide = viewModel.currentTracingGuide()

    // Coloring states
    val currentDrawingIndex by viewModel.currentDrawingIndex.collectAsState()
    val selectedColorHex by viewModel.selectedColorHex.collectAsState()
    val coloredZones by viewModel.coloredZones.collectAsState()

    // Memory states
    val memoryCards by viewModel.memoryCards.collectAsState()
    val memoryFlips by viewModel.memoryFlips.collectAsState()
    val memoryMatches by viewModel.memoryMatches.collectAsState()
    val memoryGameOver by viewModel.memoryGameOver.collectAsState()

    // Parent Dashboard states
    val assessmentReport by viewModel.assessmentReportText.collectAsState()
    val reminderEnabled by viewModel.parentReminderEnabled.collectAsState()

    // Handle back button on sub-screens
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        if (currentScreen == AppScreen.VOCABULARY) {
            viewModel.navigateTo(AppScreen.VOCAB_MENU)
        } else {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    KidsLandTheme(darkTheme = isDarkMode) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            topBar = {
                KidsTopBar(
                    currentScreen = currentScreen,
                    ageGroup = ageGroup,
                    totalStars = childProfile?.totalStars ?: 20,
                    dailyStreak = childProfile?.currentStreak ?: 1,
                    isDarkMode = isDarkMode,
                    onBackClick = {
                        if (currentScreen == AppScreen.VOCABULARY) {
                            viewModel.navigateTo(AppScreen.VOCAB_MENU)
                        } else {
                            viewModel.navigateTo(AppScreen.HOME)
                        }
                    },
                    onAgeClick = { viewModel.openAgeSelection() },
                    onThemeToggle = { viewModel.toggleDarkMode() },
                    onParentLockClick = { viewModel.navigateTo(AppScreen.PARENT_DASHBOARD) },
                    onAudioTestClick = { viewModel.speak("Welcome to KidsLand!") }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    AppScreen.HOME -> HomeScreen(
                        ageGroup = ageGroup,
                        totalStars = childProfile?.totalStars ?: 20,
                        dailyStreak = childProfile?.currentStreak ?: 1,
                        onNavigate = { viewModel.navigateTo(it) },
                        onOpenAgeDialog = { viewModel.openAgeSelection() }
                    )

                    AppScreen.VOCAB_MENU -> VocabMenuScreen(
                        onSelectCategory = { category ->
                            viewModel.setVocabCategory(category)
                            viewModel.navigateTo(AppScreen.VOCABULARY)
                        },
                        onSpeak = { viewModel.speak(it) }
                    )

                    AppScreen.VOCABULARY -> VocabularyScreen(
                        activeCategory = activeVocabCategory,
                        activeItemIndex = activeVocabIndex,
                        items = vocabItems,
                        currentlySpeakingId = currentlySpeakingId,
                        onSelectCategory = { viewModel.setVocabCategory(it) },
                        onSelectItemIndex = { viewModel.onSelectVocabIndex(it) },
                        onItemClick = { viewModel.speakVocabItem(it) },
                        onNext = { viewModel.nextVocabItem() },
                        onPrev = { viewModel.prevVocabItem() },
                        onSpeak = { viewModel.speak(it) },
                        onSpeakAll = { viewModel.speakAllInCurrentCategory() }
                    )

                    AppScreen.MATH -> MathHubScreen(
                        ageGroup = ageGroup,
                        subTab = mathSubTab,
                        countingTarget = countingTarget,
                        countingCurrent = countingCurrent,
                        mathProblem = currentMathProblem,
                        mathFeedback = mathFeedback,
                        onSubTabChanged = { viewModel.setMathSubTab(it) },
                        onTapFruit = { viewModel.tapCountFruit() },
                        onResetCounting = { viewModel.resetCounting() },
                        onMathOptionSelected = { viewModel.onMathOptionSelected(it) },
                        onSpeak = { viewModel.speak(it) }
                    )

                    AppScreen.TRACING -> TracingPadScreen(
                        guide = currentTracingGuide,
                        isAlphabetMode = isAlphabetMode,
                        onModeChanged = { viewModel.setTracingMode(it) },
                        onNext = { viewModel.nextTracingChar() },
                        onPrev = { viewModel.prevTracingChar() },
                        onComplete = { viewModel.completeTracingStroke() },
                        onSpeak = { viewModel.speak(it) }
                    )

                    AppScreen.COLORING -> ColoringBookScreen(
                        drawings = EducationalContentRegistry.animalDrawings,
                        currentIndex = currentDrawingIndex,
                        selectedColorHex = selectedColorHex,
                        coloredZones = coloredZones,
                        onSelectDrawing = { viewModel.selectDrawing(it) },
                        onSelectColorHex = { viewModel.selectColorHex(it) },
                        onColorZone = { viewModel.colorZone(it) },
                        onReset = { viewModel.resetDrawing() },
                        onSpeak = { viewModel.speak(it) }
                    )

                    AppScreen.MEMORY -> MemoryMatchScreen(
                        cards = memoryCards,
                        flips = memoryFlips,
                        matches = memoryMatches,
                        isGameOver = memoryGameOver,
                        onCardClick = { viewModel.onCardTapped(it) },
                        onRestart = { viewModel.initMemoryGame() }
                    )

                    AppScreen.REWARDS -> StickersRewardsScreen(
                        totalStars = childProfile?.totalStars ?: 20,
                        dailyStreak = childProfile?.currentStreak ?: 1,
                        onSpeak = { viewModel.speak(it) }
                    )

                    AppScreen.PARENT_DASHBOARD -> ParentDashboardScreen(
                        profile = childProfile,
                        ageGroup = ageGroup,
                        reportText = assessmentReport,
                        reminderEnabled = reminderEnabled,
                        onToggleReminder = { viewModel.toggleReminder(it) },
                        onOpenAgeSelector = { viewModel.openAgeSelection() }
                    )
                }

                // Age Selection Modal Dialog
                if (showAgeDialog) {
                    AgeSelectionDialog(
                        currentSelection = ageGroup,
                        onSelectAgeGroup = { viewModel.selectAgeGroup(it) },
                        onDismiss = { viewModel.closeAgeSelection() }
                    )
                }

                // Parent PIN / Math Gate Dialog
                if (showParentLock) {
                    ParentLockDialog(
                        onSuccess = { viewModel.onParentLockSuccess() },
                        onDismiss = { viewModel.dismissParentLock() }
                    )
                }

                // Confetti Celebration
                ConfettiOverlay(visible = showConfetti)
            }
        }
    }
}
