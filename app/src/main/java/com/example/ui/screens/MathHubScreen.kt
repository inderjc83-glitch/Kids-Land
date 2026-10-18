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
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.MathProblem
import com.example.ui.theme.BrightOrange
import com.example.ui.theme.BubblegumPink
import com.example.ui.theme.GrassGreen
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.StarGold
import com.example.ui.theme.SunshineYellow

@Composable
fun MathHubScreen(
    ageGroup: AgeGroup,
    subTab: Int,
    countingTarget: Int,
    countingCurrent: Int,
    mathProblem: MathProblem,
    mathFeedback: String?,
    onSubTabChanged: (Int) -> Unit,
    onTapFruit: () -> Unit,
    onResetCounting: () -> Unit,
    onMathOptionSelected: (Int) -> Unit,
    onSpeak: (String) -> Unit
) {
    val tabs = listOf("🍎 Count Fruits", "💯 Numbers 1-100", "🎈 Math Balloons")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("math_hub_screen")
    ) {
        // Sub-tabs
        TabRow(
            selectedTabIndex = subTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = GrassGreen
        ) {
            tabs.forEachIndexed { index, label ->
                Tab(
                    selected = subTab == index,
                    onClick = { onSubTabChanged(index) },
                    text = {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = if (subTab == index) FontWeight.Black else FontWeight.SemiBold
                        )
                    },
                    modifier = Modifier.testTag("math_sub_tab_$index")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (subTab) {
            0 -> FruitCountingView(
                target = countingTarget,
                current = countingCurrent,
                onTapFruit = onTapFruit,
                onReset = onResetCounting,
                onSpeak = onSpeak
            )
            1 -> Numbers1To100View(
                maxCount = ageGroup.countingMax,
                onSpeak = onSpeak
            )
            2 -> BalloonMathView(
                problem = mathProblem,
                feedback = mathFeedback,
                onSelectOption = onMathOptionSelected,
                onSpeak = onSpeak
            )
        }
    }
}

@Composable
private fun FruitCountingView(
    target: Int,
    current: Int,
    onTapFruit: () -> Unit,
    onReset: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val fruitEmojis = listOf("🍎", "🍓", "🍌", "🍊", "⭐", "🍇")
    val fruit = fruitEmojis[(target) % fruitEmojis.size]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, BrightOrange.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Heading 1
                Text(
                    text = "Tap the $fruit to Count to $target!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(3.dp))
                // Heading 2 placed neatly directly below Heading 1
                Text(
                    text = "Touch the big fruit button to practice counting step by step",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { if (target > 0) current.toFloat() / target else 0f },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = GrassGreen,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$current / $target Counted",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = GrassGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Big Tappable Fruit Button
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(BrightOrange.copy(alpha = 0.15f))
                .clickable { onTapFruit() }
                .testTag("tap_fruit_button"),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = fruit, fontSize = 72.sp)
                Text(
                    text = "TAP ME!",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = BrightOrange
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Visual collection of counted fruits
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your Basket:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(current) { idx ->
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surface,
                            shadowElevation = 2.dp,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = fruit, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onReset,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset Count",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Start Over", color = MaterialTheme.colorScheme.onSurface)
            }

            Button(
                onClick = { onSpeak("Counted $current out of $target $fruit") },
                colors = ButtonDefaults.buttonColors(containerColor = GrassGreen),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Speak Count",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Hear Count", color = Color.White)
            }
        }
    }
}

@Composable
private fun Numbers1To100View(
    maxCount: Int,
    onSpeak: (String) -> Unit
) {
    var highlightedNum by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Audio readout header
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, SkyBlue.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Number: $highlightedNum",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = SkyBlue
                    )
                    Text(
                        text = "Tap any number to listen voice",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = { onSpeak("Number $highlightedNum") },
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                    shape = CircleShape,
                    modifier = Modifier.size(50.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Listen",
                        tint = Color.White
                    )
                }
            }
        }

        // 1 to 100 Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(100) { index ->
                val num = index + 1
                val isSelected = num == highlightedNum
                val isInAgeFocus = num <= maxCount

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) SkyBlue else if (isInAgeFocus) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) SkyBlue else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier
                        .height(54.dp)
                        .clickable {
                            highlightedNum = num
                            onSpeak("$num")
                        }
                        .testTag("number_tile_$num")
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$num",
                            fontSize = 18.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BalloonMathView(
    problem: MathProblem,
    feedback: String?,
    onSelectOption: (Int) -> Unit,
    onSpeak: (String) -> Unit
) {
    val balloonColors = listOf(BubblegumPink, SkyBlue, BrightOrange, GrassGreen, SunshineYellow)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, StarGold.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Heading 1
                Text(
                    text = "Burst the Correct Answer Balloon! 🎈",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(3.dp))
                // Heading 2 directly underneath Heading 1
                Text(
                    text = "Solve the equation and tap the matching number balloon",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(14.dp))

                // Math Equation with visual items
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${problem.num1}", fontSize = 32.sp, fontWeight = FontWeight.Black)
                        Text(text = problem.itemEmoji.repeat(problem.num1.coerceAtMost(5)), fontSize = 16.sp)
                    }

                    Text(
                        text = " ${problem.operator} ",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = BrightOrange,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${problem.num2}", fontSize = 32.sp, fontWeight = FontWeight.Black)
                        Text(text = problem.itemEmoji.repeat(problem.num2.coerceAtMost(5)), fontSize = 16.sp)
                    }

                    Text(
                        text = " = ?",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = SkyBlue,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { onSpeak("${problem.num1} ${problem.operator} ${problem.num2}") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Speak Problem",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Read Out Problem", color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        feedback?.let { msg ->
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (msg.contains("Correct")) GrassGreen.copy(alpha = 0.2f) else BrightOrange.copy(alpha = 0.2f),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = msg,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = if (msg.contains("Correct")) GrassGreen else BrightOrange,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }
        }

        // Balloon choices
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            problem.options.forEachIndexed { index, option ->
                val color = balloonColors[index % balloonColors.size]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onSelectOption(option) }
                        .testTag("balloon_option_$option")
                ) {
                    // Balloon shape
                    Surface(
                        shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp, bottomStart = 50.dp, bottomEnd = 50.dp),
                        color = color,
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(width = 85.dp, height = 105.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "$option",
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }
                    // Balloon knot
                    Box(
                        modifier = Modifier
                            .size(10.dp, 6.dp)
                            .background(color)
                    )
                    // String
                    Box(
                        modifier = Modifier
                            .size(2.dp, 35.dp)
                            .background(Color.Gray.copy(alpha = 0.6f))
                    )
                }
            }
        }
    }
}
