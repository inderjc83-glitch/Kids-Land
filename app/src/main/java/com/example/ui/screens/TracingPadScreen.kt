package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TracingGuide
import com.example.ui.theme.BrightOrange
import com.example.ui.theme.GrassGreen
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonViolet
import com.example.ui.theme.SkyBlue

data class DrawnLine(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float = 26f
)

@Composable
fun TracingPadScreen(
    guide: TracingGuide,
    isAlphabetMode: Boolean,
    onModeChanged: (Boolean) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onComplete: () -> Unit,
    onSpeak: (String) -> Unit
) {
    // Vibrant neon ink colors for dark mode
    val inkColors = listOf(
        NeonCyan,
        BrightOrange,
        NeonLime,
        NeonPink,
        NeonViolet,
        NeonAmber
    )
    var selectedInkColor by remember { mutableStateOf(NeonCyan) }
    val lines = remember(guide.char) { mutableStateListOf<DrawnLine>() }
    var currentPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("tracing_pad_screen")
    ) {
        // Mode switch: Alphabets (A-Z) vs Numbers (0-10)
        TabRow(
            selectedTabIndex = if (isAlphabetMode) 0 else 1,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = BrightOrange
        ) {
            Tab(
                selected = isAlphabetMode,
                onClick = { onModeChanged(true) },
                text = {
                    Text(
                        text = "🔤 Alphabets (A-Z)",
                        fontWeight = if (isAlphabetMode) FontWeight.Black else FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            )
            Tab(
                selected = !isAlphabetMode,
                onClick = { onModeChanged(false) },
                text = {
                    Text(
                        text = "🔢 Numbers (0-10)",
                        fontWeight = if (!isAlphabetMode) FontWeight.Black else FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Guide Card: Heading 1 & Heading 2 stacked cleanly
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, BrightOrange.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = BrightOrange.copy(alpha = 0.2f),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = guide.hintEmoji, fontSize = 28.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    // Heading 1 & Heading 2 Stack
                    Column {
                        // Heading 1
                        Text(
                            text = "${guide.char} is for ${guide.hintWord}",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        // Heading 2 placed neatly below Heading 1
                        Text(
                            text = guide.strokeDescription,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 15.sp
                        )
                    }
                }

                // Voice Pronunciation Button
                Button(
                    onClick = { onSpeak("${guide.char}! ${guide.hintWord}. ${guide.strokeDescription}") },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = BrightOrange),
                    modifier = Modifier.size(42.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Hear Sound", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Ink palette and Action bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Colors row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                inkColors.forEach { color ->
                    val isSelected = color == selectedInkColor
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) Color.White else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedInkColor = color }
                    )
                }
            }

            // Clear and Finish buttons
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { lines.clear() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clear", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                }

                Button(
                    onClick = onComplete,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GrassGreen),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("complete_trace_button")
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Check", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Done!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fully Responsive Tracing Canvas using BoxWithConstraints
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, selectedInkColor.copy(alpha = 0.45f)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
                .testTag("tracing_canvas_card")
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                val density = LocalDensity.current
                val maxH = maxHeight
                val maxW = maxWidth

                // Dynamically scale font size according to screen height & width and character count
                // (e.g. "10" gets smaller so it never overflows or wraps awkwardly!)
                val responsiveFontSize = remember(guide.char, maxH, maxW) {
                    val isTwoDigit = guide.char.length > 1
                    val baseFactor = if (isTwoDigit) 0.35f else 0.50f
                    val calculatedSp = (maxH.value * baseFactor).coerceIn(70f, 175f)
                    calculatedSp.sp
                }

                // Decorative guidelines (Top line, middle dashed line, baseline)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    val topY = h * 0.18f
                    val midY = h * 0.50f
                    val botY = h * 0.82f

                    val guideColor = Color(0xFF394867).copy(alpha = 0.45f)
                    val dashedEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 14f), 0f)

                    // Top solid line
                    drawLine(guideColor, Offset(16f, topY), Offset(w - 16f, topY), strokeWidth = 2.5f)
                    // Mid dashed line
                    drawLine(guideColor, Offset(16f, midY), Offset(w - 16f, midY), strokeWidth = 2.5f, pathEffect = dashedEffect)
                    // Bottom solid baseline
                    drawLine(guideColor, Offset(16f, botY), Offset(w - 16f, botY), strokeWidth = 2.5f)
                }

                // High-visibility semi-transparent Guide Character
                Text(
                    text = guide.char,
                    fontSize = responsiveFontSize,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.22f),
                    textAlign = TextAlign.Center
                )

                // Start and Finish Helper Badges
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "🟢 Start at top",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonLime,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Text(
                        text = "⭐ Finish",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonAmber,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }

                // Interactive touch canvas for kids finger tracing
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(guide.char) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentPoints = listOf(offset)
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    currentPoints = currentPoints + change.position
                                },
                                onDragEnd = {
                                    if (currentPoints.isNotEmpty()) {
                                        lines.add(DrawnLine(currentPoints, selectedInkColor))
                                        currentPoints = emptyList()
                                    }
                                }
                            )
                        }
                ) {
                    // Draw previous lines
                    lines.forEach { line ->
                        if (line.points.size > 1) {
                            val path = Path()
                            path.moveTo(line.points.first().x, line.points.first().y)
                            for (i in 1 until line.points.size) {
                                path.lineTo(line.points[i].x, line.points[i].y)
                            }
                            drawPath(
                                path = path,
                                color = line.color,
                                style = Stroke(
                                    width = line.strokeWidth,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                            )
                        }
                    }

                    // Draw active drag stroke
                    if (currentPoints.size > 1) {
                        val path = Path()
                        path.moveTo(currentPoints.first().x, currentPoints.first().y)
                        for (i in 1 until currentPoints.size) {
                            path.lineTo(currentPoints[i].x, currentPoints[i].y)
                        }
                        drawPath(
                            path = path,
                            color = selectedInkColor,
                            style = Stroke(
                                width = 26f,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Next and Prev Character Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onPrev,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = CircleShape,
                modifier = Modifier.size(46.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous Character",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "Trace with your finger! 👆",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = CircleShape,
                modifier = Modifier.size(46.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next Character",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
