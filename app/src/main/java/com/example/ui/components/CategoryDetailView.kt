package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VocabCategory
import com.example.model.VocabItem
import com.example.ui.theme.StarGold

@Composable
fun CategoryDetailView(
    category: VocabCategory,
    items: List<VocabItem>,
    currentlySpeakingId: String?,
    onItemClick: (VocabItem) -> Unit,
    onSpeakAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("category_detail_view")
    ) {
        // Category Header Summary Bar: Heading 1 & Heading 2 strictly vertical
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = category.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    // Heading 1 & Heading 2 Stack
                    Column {
                        // Heading 1
                        Text(
                            text = "All ${category.title} (${items.size})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        // Heading 2 directly underneath Heading 1
                        Text(
                            text = "Tap any card to speak and learn",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Listen All button
                Button(
                    onClick = onSpeakAll,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("speak_all_${category.name.lowercase()}_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Listen All",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Listen All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Large Cards Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items, key = { it.id }) { item ->
                val isSpeaking = item.id == currentlySpeakingId
                LargeVocabCard(
                    item = item,
                    isSpeaking = isSpeaking,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun LargeVocabCard(
    item: VocabItem,
    isSpeaking: Boolean,
    onClick: () -> Unit
) {
    val itemColor = Color(item.colorHex)

    // Animated scale on speaking
    val scale by animateFloatAsState(
        targetValue = if (isSpeaking) 1.03f else 1.0f,
        animationSpec = spring(),
        label = "card_scale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSpeaking) StarGold else itemColor.copy(alpha = 0.5f),
        label = "border_color"
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSpeaking) itemColor.copy(alpha = 0.16f) else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(if (isSpeaking) 2.5.dp else 1.5.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSpeaking) 6.dp else 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { onClick() }
            .testTag("large_card_${item.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top: Illustration Box with Large Emoji
            Surface(
                shape = CircleShape,
                color = itemColor.copy(alpha = 0.2f),
                modifier = Modifier.size(76.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = item.emoji,
                        fontSize = 42.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Heading 1: Word Name (Large & Bold English)
            Text(
                text = item.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Heading 2: Subtitle Guide directly underneath Heading 1
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = item.phoneticHint,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = itemColor,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Fun Fact / Description snippet
            Text(
                text = item.funFact,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tap-to-Speak Action Pill Button
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSpeaking) StarGold else itemColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .testTag("speak_btn_${item.id}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Listen ${item.name}",
                        tint = if (isSpeaking) Color.Black else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isSpeaking) "Speaking... 🔊" else "Tap to Speak",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSpeaking) Color.Black else Color.White
                    )
                }
            }
        }
    }
}
