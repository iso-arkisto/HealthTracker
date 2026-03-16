package com.yourname.healthtracker.presentation.components

import android.content.res.Configuration
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.ui.theme.HealthTrackerTheme
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.foundation.Canvas
import androidx.compose.ui.res.stringResource
import com.yourname.healthtracker.R

@Composable
fun HRVWidget(
    data: List<Float>,
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
            text = stringResource(R.string.hrv),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "(${stringResource(R.string.recovery)})",
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        HRVChart(data = data)

        Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = data.last().toInt().toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.milliseconds),
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 3.dp, top = 1.dp)
            )
       }

    }
}

@Composable
fun HRVChart(
    data: List<Float>,
    modifier: Modifier = Modifier
        .height(50.dp)
        .width(120.dp),
    lineColor: Color = Color(0xFF81D4FA),
    lineWidth: Float = 4f
) {
    Canvas(modifier = modifier.padding(8.dp)) {

        if (data.size < 2) return@Canvas

        val distance = size.width / (data.size - 1)
        val maxVal = data.maxOrNull() ?: 0f
        val minVal = data.minOrNull() ?: 0f
        val range = (maxVal - minVal).coerceAtLeast(1f)

        fun getX(index: Int) = index * distance
        fun getY(value: Float) = size.height - ((value - minVal) / range * size.height)

        val strokePath = Path().apply {
            moveTo(getX(0), getY(data[0]))
            for (i in 1 until data.size) {
                val prevX = getX(i - 1)
                val prevY = getY(data[i - 1])
                val currX = getX(i)
                val currY = getY(data[i])

                cubicTo(
                    (prevX + currX) / 2, prevY,
                    (prevX + currX) / 2, currY,
                    currX, currY
                )
            }
        }

        val fillPath = Path().apply {
            addPath(strokePath)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    lineColor.copy(alpha = 0.4f),
                    Color.Transparent
                ),
                startY = 0f,
                endY = size.height
            )
        )

        drawPath(
            path = strokePath,
            color = lineColor,
            style = Stroke(width = lineWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
    )
fun PrevHRV() {
    HealthTrackerTheme {
        HRVWidget(
            data = listOf(50f,60f,65f,62f,10f)
        )
    }
}