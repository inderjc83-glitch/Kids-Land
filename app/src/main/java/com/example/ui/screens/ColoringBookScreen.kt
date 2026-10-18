package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnimalDrawing
import com.example.ui.theme.BerryPurple
import com.example.ui.theme.BrightOrange
import com.example.ui.theme.BubblegumPink
import com.example.ui.theme.GrassGreen
import com.example.ui.theme.OceanBlue
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.StarGold
import com.example.ui.theme.SunnyAmber

@Composable
fun ColoringBookScreen(
    drawings: List<AnimalDrawing>,
    currentIndex: Int,
    selectedColorHex: Long,
    coloredZones: Map<String, Long>,
    onSelectDrawing: (Int) -> Unit,
    onSelectColorHex: (Long) -> Unit,
    onColorZone: (String) -> Unit,
    onReset: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val palette = listOf(
        0xFFE53935, // Red
        0xFF1E88E5, // Blue
        0xFF43A047, // Green
        0xFFFFD600, // Yellow
        0xFFFF9800, // Orange
        0xFF8E24AA, // Purple
        0xFFEC407A, // Pink
        0xFF795548, // Brown
        0xFF455A64  // Slate
    )

    val currentAnimal = drawings[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("coloring_book_screen")
    ) {
        // Animal Carousel Selector
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(drawings) { index, animal ->
                val isSelected = index == currentIndex
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) BubblegumPink.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) BubblegumPink else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier
                        .clickable { onSelectDrawing(index) }
                        .testTag("animal_drawing_item_$index")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = animal.emoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = animal.name,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Animal Drawing Canvas and Interactive Zones
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, BubblegumPink.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with Voice Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = currentAnimal.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Habitat: ${currentAnimal.habitat}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Button(
                        onClick = { onSpeak("${currentAnimal.name}. ${currentAnimal.funFact}") },
                        colors = ButtonDefaults.buttonColors(containerColor = BubblegumPink),
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Hear animal fact", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Central Animal Illustration with Colorable Zones
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentAnimal.emoji,
                        fontSize = 90.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tap a body part to paint it with chosen color! 🖌️",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Colorable Zones in 2-Column Grid
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    currentAnimal.defaultSvgShapes.chunked(2).forEach { rowZones ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowZones.forEach { zone ->
                                val zoneColorHex = coloredZones[zone.zoneId] ?: 0xFFFFFFFF
                                val isColored = coloredZones.containsKey(zone.zoneId)

                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(zoneColorHex),
                                    border = BorderStroke(2.dp, if (isColored) Color(zoneColorHex) else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                                    shadowElevation = 2.dp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clickable { onColorZone(zone.zoneId) }
                                        .testTag("color_zone_${zone.zoneId}")
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = zone.label,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if (isColored && zoneColorHex != 0xFFFFFFFF) Color.White else Color.Black,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = if (isColored) "✓" else "🎨",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isColored && zoneColorHex != 0xFFFFFFFF) Color.White else Color.Gray
                                        )
                                    }
                                }
                            }
                            if (rowZones.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Color Palette & Reset Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(palette.size) { i ->
                    val colorHex = palette[i]
                    val isSelected = colorHex == selectedColorHex
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(colorHex))
                            .border(
                                width = if (isSelected) 3.5.dp else 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { onSelectColorHex(colorHex) }
                            .testTag("palette_color_$i")
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onReset,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset Color", tint = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Erase", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
            }
        }
    }
}
