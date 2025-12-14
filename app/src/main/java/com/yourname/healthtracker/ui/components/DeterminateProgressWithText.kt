package com.yourname.healthtracker.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourname.healthtracker.ui.theme.RedTransparent
import com.yourname.healthtracker.ui.theme.WaterColor
import kotlinx.coroutines.launch

@Composable
fun DeterminateProgressWithText(title: String, progress: Float, color: Color, modifier: Modifier) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )
    var background = Modifier.background(Color.Transparent)

    if(progress > 1.0f) {
        background = Modifier.background(RedTransparent)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = background
    ) {
        Text(
            text = title,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            contentAlignment = Alignment.Center,

        ) {
            CircularProgressIndicator(
                progress = { animatedProgress },
                modifier = modifier,
                color = color,

            )
            Text(
               text = "${(animatedProgress*100).toInt()}%",
                fontSize = 20.sp
            )
        }

    }
}