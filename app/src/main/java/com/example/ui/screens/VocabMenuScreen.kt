package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VocabCategory
import com.example.ui.theme.BerryPurple
import com.example.ui.theme.BrightOrange
import com.example.ui.theme.BubblegumPink
import com.example.ui.theme.GrassGreen
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.OceanBlue
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunnyAmber
import com.example.ui.theme.SunshineYellow

data class CategoryMenuItem(
    val category: VocabCategory,
    val title: String,
    val subtitle: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val previewItems: List<String>,
    val audioSpeechText: String
)

@Composable
fun VocabMenuScreen(
    onSelectCategory: (VocabCategory) -> Unit,
    onSpeak: (String) -> Unit
) {
    // English-only with polite voice copy and no names or badges displayed outside on cards
    val menuItems = listOf(
        CategoryMenuItem(
            category = VocabCategory.COLORS,
            title = "Colors",
            subtitle = "Bright & Beautiful Hues",
            primaryColor = BubblegumPink,
            secondaryColor = BerryPurple,
            previewItems = listOf("🔴", "🔵", "🟢", "🟡", "🟣"),
            audioSpeechText = "Colors. Explore bright colors like Red, Blue, Green, Yellow, Orange, and Pink."
        ),
        CategoryMenuItem(
            category = VocabCategory.ANIMALS,
            title = "Animals",
            subtitle = "Safari & Pet Friends",
            primaryColor = SunnyAmber,
            secondaryColor = BrightOrange,
            previewItems = listOf("🦁", "🐘", "🐅", "🐒", "🦒"),
            audioSpeechText = "Animals. Gentle wild friends and pets: Lion, Elephant, Tiger, Monkey, and Giraffe."
        ),
        CategoryMenuItem(
            category = VocabCategory.BIRDS,
            title = "Birds",
            subtitle = "Feathered Sky Friends",
            primaryColor = SkyBlue,
            secondaryColor = OceanBlue,
            previewItems = listOf("🦜", "🦚", "🦅", "🦉", "🦆"),
            audioSpeechText = "Birds. Flying feather friends: Parrot, Peacock, Eagle, Owl, and Duck."
        ),
        CategoryMenuItem(
            category = VocabCategory.MONTHS,
            title = "Months",
            subtitle = "Calendar Seasons",
            primaryColor = GrassGreen,
            secondaryColor = NeonCyan,
            previewItems = listOf("❄️", "🌱", "☀️", "🌧️", "🎄"),
            audioSpeechText = "Months. Twelve months of the year from January to December."
        ),
        CategoryMenuItem(
            category = VocabCategory.DAYS,
            title = "Weekdays",
            subtitle = "Seven Days of the Week",
            primaryColor = BrightOrange,
            secondaryColor = SunshineYellow,
            previewItems = listOf("🎒", "🎨", "⚽", "🔬", "🚲"),
            audioSpeechText = "Weekdays. Seven days of the week: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, and Sunday."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("vocab_menu_screen")
    ) {
        // Welcoming Header Banner: Heading 1 & Heading 2 cleanly ordered
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, SkyBlue.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = SkyBlue.copy(alpha = 0.2f),
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🌟", fontSize = 28.sp)
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                // Heading 1 & Heading 2 directly vertical
                Column(modifier = Modifier.weight(1f)) {
                    // Heading 1
                    Text(
                        text = "Learning Topics Menu",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    // Heading 2 (Neatly placed under Heading 1)
                    Text(
                        text = "Tap any topic to listen to clear, soft audio pronunciation",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Grid Menu
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(menuItems) { item ->
                CategoryGridCard(
                    item = item,
                    onCardClick = {
                        onSpeak(item.audioSpeechText)
                        onSelectCategory(item.category)
                    },
                    onAudioClick = {
                        onSpeak(item.audioSpeechText)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryGridCard(
    item: CategoryMenuItem,
    onCardClick: () -> Unit,
    onAudioClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, item.primaryColor.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("category_menu_item_${item.category.name.lowercase()}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Row: Audio Pronunciation Button on top-right (no clutter, no side tags)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onAudioClick,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(item.primaryColor.copy(alpha = 0.2f))
                        .testTag("speak_topic_${item.category.name.lowercase()}")
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Pronounce ${item.title}",
                        tint = item.primaryColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // High Quality Illustration Box with Dark Mode Compatible Surface
            CategoryIllustrationBox(item = item)

            Spacer(modifier = Modifier.height(12.dp))

            // Heading 1: Category Title
            Text(
                text = item.title,
                fontSize = 19.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Heading 2: Subtitle placed directly underneath Heading 1
            Text(
                text = item.subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = item.primaryColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            // "Explore & Listen" Action Button
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = item.primaryColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCardClick() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Explore & Listen",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryIllustrationBox(item: CategoryMenuItem) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = item.primaryColor.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, item.primaryColor.copy(alpha = 0.35f)),
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (item.category) {
                VocabCategory.COLORS -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🎨", fontSize = 38.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "🔴", fontSize = 12.sp)
                            Text(text = "🔵", fontSize = 12.sp)
                            Text(text = "🟢", fontSize = 12.sp)
                            Text(text = "🟡", fontSize = 12.sp)
                            Text(text = "🟣", fontSize = 12.sp)
                        }
                    }
                }
                VocabCategory.ANIMALS -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🦁", fontSize = 36.sp)
                            Text(text = "🐘", fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "🐅", fontSize = 14.sp)
                            Text(text = "🐒", fontSize = 14.sp)
                            Text(text = "🦒", fontSize = 14.sp)
                        }
                    }
                }
                VocabCategory.BIRDS -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🦜", fontSize = 34.sp)
                            Text(text = "🦚", fontSize = 34.sp)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "🦅", fontSize = 14.sp)
                            Text(text = "🦉", fontSize = 14.sp)
                            Text(text = "🦆", fontSize = 14.sp)
                        }
                    }
                }
                VocabCategory.MONTHS -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📅", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "❄️", fontSize = 13.sp)
                            Text(text = "🌱", fontSize = 13.sp)
                            Text(text = "☀️", fontSize = 13.sp)
                            Text(text = "🍂", fontSize = 13.sp)
                        }
                    }
                }
                VocabCategory.DAYS -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "☀️", fontSize = 36.sp)
                            Text(text = "🎒", fontSize = 26.sp)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "🎨", fontSize = 13.sp)
                            Text(text = "⚽", fontSize = 13.sp)
                            Text(text = "🚲", fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}
