package com.yourname.healthtracker.presentation.components

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.R

@Composable
fun SleepWindowWidget(
    wakeUpTime: String,
    bedTime: String,
    sleepDuration: String
) {
    Column(
        modifier = Modifier
            .size(170.dp)
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val composePaint = Paint().apply {
                        color = Color.Gray.copy(alpha = 0.3f)
                        asFrameworkPaint().apply {
                            maskFilter = BlurMaskFilter(40f, BlurMaskFilter.Blur.NORMAL)
                        }
                    }

                    canvas.drawCircle(
                        center = Offset(x = size.width/2f, y = size.height/2f),
                        radius = size.minDimension / 2,
                        paint = composePaint
                    )
                }
            }
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SLEEP\nWINDOW",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(9.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.bed_icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )

            SleepProgressBar(
                modifier = Modifier
                    .weight(1f)
                    .height(7.dp)
                    .padding(horizontal = 4.dp)
            )

            Icon(
                painter = painterResource(R.drawable.alarm_clock),
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(modifier = Modifier.height(9.dp))

        Text(
            text = "$bedTime → $wakeUpTime",
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "($sleepDuration)",
            fontSize = 13.sp,
            color = Color.Gray
        )

    }
}

@Composable
fun SleepProgressBar(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val trackColor = Color(0xFF2D353B)
        val progressBrush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF4A5D69),
                Color(0xFF8EDAE5)
            )
        )

        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = CornerRadius(x = size.height / 2, y = size.height / 2)
        )

        drawRoundRect(
            brush = progressBrush,
            topLeft = Offset(x = size.width * 0.1f, y = 0f),
            size = Size(width = size.width * 0.8f, height = size.height),
            cornerRadius = CornerRadius(x = size.height / 2, y = size.height / 2)
        )
    }
}

@Preview
@Composable
fun PreviewWidget() {
    SleepWindowWidget(
        wakeUpTime = "2:00",
        bedTime = "23:00",
        sleepDuration = "7h 20m"
    )
}
