package com.yourname.healthtracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun ThreeColorCircularProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = 1f,
    strokeWidth: Float = 10f,
    color1: Color = Color.Blue,
    color2: Color = Color.Yellow,
    color3: Color = Color.Green,
    percent1: Float = 0.3f,
    percent2: Float = 0.4f,
    percent3: Float = 0.3f
) {
    Canvas(modifier = modifier) {
        val sweepAngleTotal = 360f * progress

        val totalPercents = percent1 + percent2 + percent3

        val sweepAngle1 = sweepAngleTotal * (percent1 / totalPercents)
        val sweepAngle2 = sweepAngleTotal * (percent2 / totalPercents)
        val sweepAngle3 = sweepAngleTotal * (percent3 / totalPercents)

        drawArc(
            color = Color.LightGray,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = color1,
            startAngle = -90f,
            sweepAngle = sweepAngle1,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        drawArc(
            color = color2,
            startAngle = -90f + sweepAngle1,
            sweepAngle = sweepAngle2,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        drawArc(
            color = color3,
            startAngle = -90f + sweepAngle1 + sweepAngle2,
            sweepAngle = sweepAngle3,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}