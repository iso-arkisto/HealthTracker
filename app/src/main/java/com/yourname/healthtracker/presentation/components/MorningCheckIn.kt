package com.yourname.healthtracker.presentation.components

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.R

val AccentCyan = Color(0xFFB2EBF2)
val TagBackground = Color(0xFF2C363A)

@Composable
@Preview
fun MorningCheckIn() {
    var selectedMood by remember { mutableIntStateOf(2) }
    val tags = listOf("Late Meal", "Caffeine", "Alcohol", "Blue Light", "Screens < 1h")
    val emojis = listOf("☹️", "🙁", "😐", "🙂", "😁")


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(.1f),
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MORNING CHECK-IN",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "FEELING",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
//                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                        emojis.forEachIndexed { index, emoji ->
//                            MoodIcon(
//                                emoji = emoji,
//                                isSelected = selectedMood == index,
//                                onClick = { selectedMood = index }
//                            )
//                        }
//                    }
                    LazyRow(modifier = Modifier.fillMaxWidth(   )) {
                        items(emojis) { emoji ->
                            MoodIcon(
                                emoji = emoji,
                                isSelected = selectedMood == emojis.indexOf(emoji),
                                onClick = { selectedMood = emojis.indexOf(emoji) }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(80.dp)
                        .background(Color.DarkGray)
                        .padding(horizontal = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "TAGS",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(tags) { tag ->
                            TagChip(text = tag)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoodIcon(emoji: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(if (isSelected) AccentCyan else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp,
            color = if (isSelected) Color.Black else Color.White
        )
    }
}

@Composable
fun TagChip(text: String) {
    var isSelected by rememberSaveable { mutableStateOf(false) }

    FilterChip(
        selected = isSelected,
        onClick = { isSelected = !isSelected },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = AccentCyan,
            selectedLabelColor = MaterialTheme.colorScheme.surface
        ),
        leadingIcon = {
            if (isSelected) {
                Icon(
                    painter = painterResource(R.drawable.done_icon),
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                    tint = if(isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )

}
