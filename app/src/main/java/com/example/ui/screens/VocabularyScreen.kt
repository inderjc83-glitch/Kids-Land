package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VocabCategory
import com.example.model.VocabItem
import com.example.ui.components.CategoryDetailView

@Composable
fun VocabularyScreen(
    activeCategory: VocabCategory,
    activeItemIndex: Int,
    items: List<VocabItem>,
    currentlySpeakingId: String?,
    onSelectCategory: (VocabCategory) -> Unit,
    onSelectItemIndex: (Int) -> Unit,
    onItemClick: (VocabItem) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onSpeak: (String) -> Unit,
    onSpeakAll: () -> Unit
) {
    var isGridViewMode by remember { mutableStateOf(true) }
    val currentItem = items.getOrNull(activeItemIndex) ?: items.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("vocabulary_screen")
    ) {
        // Category Horizontal Menu Bar
        ScrollableTabRow(
            selectedTabIndex = activeCategory.ordinal,
            edgePadding = 12.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            VocabCategory.entries.forEach { category ->
                val isSelected = category == activeCategory
                Tab(
                    selected = isSelected,
                    onClick = { onSelectCategory(category) },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 6.dp)
                        ) {
                            Text(text = category.emoji, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = category.title,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }
                    },
                    modifier = Modifier.testTag("vocab_tab_${category.name.lowercase()}")
                )
            }
        }

        // View Mode Switcher: Heading 1 & Heading 2 on left, buttons on right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                // Heading 1
                Text(
                    text = "${activeCategory.title} (${items.size} Items)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                // Heading 2
                Text(
                    text = activeCategory.subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isGridViewMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .clickable { isGridViewMode = true }
                        .testTag("toggle_grid_mode")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Grid",
                            tint = if (isGridViewMode) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Grid",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isGridViewMode) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (!isGridViewMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .clickable { isGridViewMode = false }
                        .testTag("toggle_card_mode")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Style,
                            contentDescription = "Flashcard",
                            tint = if (!isGridViewMode) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Card",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (!isGridViewMode) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Display Content Mode
        if (isGridViewMode) {
            CategoryDetailView(
                category = activeCategory,
                items = items,
                currentlySpeakingId = currentlySpeakingId,
                onItemClick = onItemClick,
                onSpeakAll = onSpeakAll,
                modifier = Modifier.weight(1f)
            )
        } else {
            SingleFlashcardView(
                items = items,
                activeItemIndex = activeItemIndex,
                currentItem = currentItem,
                onSelectItemIndex = onSelectItemIndex,
                onNext = onNext,
                onPrev = onPrev,
                onSpeak = onSpeak,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SingleFlashcardView(
    items: List<VocabItem>,
    activeItemIndex: Int,
    currentItem: VocabItem?,
    onSelectItemIndex: (Int) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (currentItem == null) return

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = currentItem,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "card_anim",
            modifier = Modifier.fillMaxWidth()
        ) { item ->
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(2.dp, Color(item.colorHex).copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(item.colorHex).copy(alpha = 0.2f),
                        modifier = Modifier
                            .size(96.dp)
                            .clickable { onSpeak("${item.name}. ${item.funFact}") }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = item.emoji, fontSize = 50.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Heading 1: Item Name
                    Text(
                        text = item.name,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    // Heading 2: Subtitle Guide directly underneath Heading 1
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = item.phoneticHint,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.funFact,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Listen Voice Action Button (English only)
                    Button(
                        onClick = { onSpeak("${item.name}! ${item.funFact}") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(item.colorHex)),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(50.dp)
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Listen Pronunciation", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = onPrev,
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            modifier = Modifier.size(44.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                        }

                        Text(
                            text = "${activeItemIndex + 1} of ${items.size}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Button(
                            onClick = onNext,
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            modifier = Modifier.size(44.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Mini preview grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 75.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(items) { index, item ->
                val isCurrent = index == activeItemIndex
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCurrent) Color(item.colorHex).copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        width = if (isCurrent) 2.dp else 1.dp,
                        color = if (isCurrent) Color(item.colorHex) else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                    ),
                    modifier = Modifier.clickable {
                        onSelectItemIndex(index)
                        onSpeak(item.name)
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = item.emoji, fontSize = 24.sp)
                        Text(
                            text = item.name,
                            fontSize = 11.sp,
                            fontWeight = if (isCurrent) FontWeight.Black else FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
