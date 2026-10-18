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
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgeGroup
import com.example.model.AppScreen
import com.example.ui.theme.BerryPurple
import com.example.ui.theme.BrightOrange
import com.example.ui.theme.BubblegumPink
import com.example.ui.theme.GrassGreen
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunshineYellow

data class ActivityTile(
    val screen: AppScreen,
    val title: String,
    val subtitle: String,
    val emoji: String,
    val primaryColor: Color
)

@Composable
fun HomeScreen(
    ageGroup: AgeGroup,
    totalStars: Int,
    dailyStreak: Int,
    onNavigate: (AppScreen) -> Unit,
    onOpenAgeDialog: () -> Unit
) {
    // Grid View active by default as requested
    var isGridView by remember { mutableStateOf(true) }

    // Stories removed as requested; clean focus on learning modules
    val activities = listOf(
        ActivityTile(
            screen = AppScreen.VOCAB_MENU,
            title = "Learn Words & Topics",
            subtitle = "Interactive audio flashcards and spoken words",
            emoji = "🗣️",
            primaryColor = SkyBlue
        ),
        ActivityTile(
            screen = AppScreen.MATH,
            title = "Math & Numbers",
            subtitle = "Count fruits, 1-100 chart, balloon math",
            emoji = "🔢",
            primaryColor = GrassGreen
        ),
        ActivityTile(
            screen = AppScreen.TRACING,
            title = "Alphabet & Tracing",
            subtitle = "Handwriting practice on digital pad",
            emoji = "✍️",
            primaryColor = BrightOrange
        ),
        ActivityTile(
            screen = AppScreen.COLORING,
            title = "Animal Coloring",
            subtitle = "Fill vibrant colors in cute animal drawings",
            emoji = "🎨",
            primaryColor = BubblegumPink
        ),
        ActivityTile(
            screen = AppScreen.MEMORY,
            title = "Shapes & Memory",
            subtitle = "Fun card flip puzzle for memory and focus",
            emoji = "🧩",
            primaryColor = BerryPurple
        ),
        ActivityTile(
            screen = AppScreen.REWARDS,
            title = "Badges & Stickers",
            subtitle = "Milestone badges and unlocked rewards",
            emoji = "🏆",
            primaryColor = SunshineYellow
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen")
    ) {
        // Welcoming Header Card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(1.5.dp, Color(ageGroup.colorHex).copy(alpha = 0.5f)),
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
                    color = Color(ageGroup.colorHex).copy(alpha = 0.22f),
                    modifier = Modifier.size(54.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = ageGroup.avatarEmoji, fontSize = 30.sp)
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                // Heading 1 & Heading 2 cleanly ordered vertically
                Column(modifier = Modifier.weight(1f)) {
                    // Heading 1
                    Text(
                        text = "Hello, Little Learner!",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Heading 2 directly underneath Heading 1
                    Text(
                        text = "Curriculum: ${ageGroup.title} (${ageGroup.ageLabel})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(ageGroup.colorHex)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Stars Progress Indicator
                    val dailyGoal = 50
                    val progress = (totalStars % dailyGoal) / dailyGoal.toFloat()
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = SunshineYellow,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "⭐ $totalStars Total Stars",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunshineYellow
                        )
                        Text(
                            text = "🔥 $dailyStreak Days Streak",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrightOrange
                        )
                    }
                }
            }
        }

        // Section Title Area: Heading 1 & Heading 2 stacked, plus Grid/List toggle & Change Age
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Heading 1 & Heading 2 Column
            Column(modifier = Modifier.weight(1f)) {
                // Heading 1
                Text(
                    text = "Learning Adventures",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                // Heading 2 directly underneath Heading 1
                Text(
                    text = if (isGridView) "Tap any card below to play" else "Select an adventure to explore",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Grid / List View Toggle Switch
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Row(modifier = Modifier.padding(2.dp)) {
                        // Grid Toggle Button
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isGridView) MaterialTheme.colorScheme.primary else Color.Transparent,
                            modifier = Modifier
                                .clickable { isGridView = true }
                                .testTag("toggle_home_grid")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.GridView,
                                    contentDescription = "Grid View",
                                    tint = if (isGridView) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Grid",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isGridView) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // List Toggle Button
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (!isGridView) MaterialTheme.colorScheme.primary else Color.Transparent,
                            modifier = Modifier
                                .clickable { isGridView = false }
                                .testTag("toggle_home_list")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Style,
                                    contentDescription = "List View",
                                    tint = if (!isGridView) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "List",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (!isGridView) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Change Age Quick Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.clickable { onOpenAgeDialog() }
                ) {
                    Text(
                        text = "Change Age",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Activities Grid / List View
        LazyVerticalGrid(
            columns = if (isGridView) GridCells.Fixed(2) else GridCells.Fixed(1),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(activities) { act ->
                if (isGridView) {
                    // Modern 2-Column Grid Card
                    HomeActivityGridCard(
                        act = act,
                        onClick = { onNavigate(act.screen) }
                    )
                } else {
                    // 1-Column List Card
                    HomeActivityListCard(
                        act = act,
                        onClick = { onNavigate(act.screen) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeActivityGridCard(
    act: ActivityTile,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.5.dp, act.primaryColor.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("activity_card_${act.screen.name.lowercase()}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Emoji Bubble
            Surface(
                shape = CircleShape,
                color = act.primaryColor.copy(alpha = 0.2f),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = act.emoji, fontSize = 30.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Heading 1: Title
            Text(
                text = act.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Heading 2: Subtitle directly underneath Heading 1
            Text(
                text = act.subtitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Play / Explore Pill Action
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = act.primaryColor.copy(alpha = 0.18f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Play",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = act.primaryColor
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = act.primaryColor,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeActivityListCard(
    act: ActivityTile,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.5.dp, act.primaryColor.copy(alpha = 0.45f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("activity_card_${act.screen.name.lowercase()}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = act.primaryColor.copy(alpha = 0.2f),
                modifier = Modifier.size(54.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = act.emoji, fontSize = 28.sp)
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // In-Card: Heading 1 & Heading 2 stacked cleanly
            Column(modifier = Modifier.weight(1f)) {
                // Heading 1
                Text(
                    text = act.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(3.dp))

                // Heading 2 (Subtitle) directly underneath Heading 1
                Text(
                    text = act.subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open ${act.title}",
                tint = act.primaryColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
