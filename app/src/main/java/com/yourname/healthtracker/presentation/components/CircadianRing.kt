package com.yourname.healthtracker.presentation.components

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.R

@Composable
fun CircadianRing(
    @FloatRange(from = 0.0, to = 1.0) dayFraction: Float = 0.7f,
    @FloatRange(from = 0.0, to = 1.0) actualSleepProgress: Float = 0.8f,
    alignmentScore: Int = 87,
    consistency: Int = R.string.high,
    sleepDuration: String = "7h 20m",
) {
    Box(
        modifier = Modifier
            .size(280.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(250.dp)) {
            val center = Offset(size.width / 2, size.height / 2)
            val outerRadius = size.width / 2
            val innerRingRadius = outerRadius - 25.dp.toPx()
            val strokeWidth = 10.dp.toPx()

            val orangeColor = Color(0xFFFF9800)
            val blueColor = Color(0xFF3F51B5)

//            val sweepGradient = Brush.sweepGradient(
//                0.0f to blueColor,
//                ((1f - dayFraction) / 2) to blueColor,
//                ((1f - dayFraction) / 2) to orangeColor,
//
//                ((1f + dayFraction) / 2) to orangeColor,
//                ((1f + dayFraction) / 2) to blueColor,
//                1.0f to blueColor
//            )

            val orangeCenter = 0.75f
            val halfOrange = dayFraction/2

            val sweepGradient = Brush.sweepGradient(
                0.0f to blueColor,
                (orangeCenter - halfOrange) to blueColor,
                orangeCenter to orangeColor,
                (orangeCenter + halfOrange) to blueColor,
                1.0f to blueColor
            )

//            val sweepGradient = Brush.sweepGradient(
//                0.0f to blueColor,
//                (0.5f - dayFraction / 2) to blueColor,
//                0.5f to orangeColor,
//                (0.5f + dayFraction / 2) to blueColor,
//                1.0f to blueColor
//            )

            drawArc(
                brush = sweepGradient,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 4.dp.toPx())
            )

            val innerTrackColor = Color(0xFF4DD0E1).copy(alpha = 0.2f)
            val innerActiveColor = Color(0xFF4DD0E1)

            drawCircle(
                color = innerTrackColor,
                radius = innerRingRadius,
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                color = innerActiveColor,
                startAngle = -120f,
                sweepAngle = 360f * actualSleepProgress,
                useCenter = false,
                topLeft = Offset(center.x - innerRingRadius, center.y - innerRingRadius),
                size = Size(innerRingRadius * 2, innerRingRadius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier.offset(y = (-100).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.light_dark_cycle).uppercase(), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.circadian_rhythm).capitalize(), color = Color.Gray, fontSize = 12.sp)
            }

//            Column(
//                modifier = Modifier.offset(y = (-20).dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(stringResource(R.string.actual_sleep).uppercase(), fontSize = 12.sp, fontWeight = FontWeight.Bold)
//                Text(stringResource(R.string.last_night_sleep).capitalize(), color = Color.Gray, fontSize = 10.sp)
//            }

            Column(
                modifier = Modifier.offset(y = (-25).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.alignment).uppercase()+":", color = Color.Gray, fontSize = 14.sp)
                Text("$alignmentScore%", fontSize = 44.sp, fontWeight = FontWeight.ExtraBold)
                Text(stringResource(R.string.consistency).uppercase()+":", color = Color.Gray, fontSize = 14.sp)
                Text(stringResource(consistency).uppercase(), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }




        }
    }
}

@Composable
@Preview
fun PrevRing() {
    CircadianRing(
        dayFraction = .4f,
        actualSleepProgress = .6f,
        consistency = R.string.high,
        alignmentScore = 60
    )
}