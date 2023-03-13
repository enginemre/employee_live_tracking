package com.hakmar.employeelivetracking.features.store_detail.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Warning

@Composable
fun CompletedStatusProgressBar(
    modifier: Modifier = Modifier,
    size: Dp,
    percentage: Float,
    taskCount: Int
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 1000,
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Column(modifier = modifier) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(size)) {
                drawArc(
                    color = Warning,
                    startAngle = -90f,
                    sweepAngle = 360 * currentPercentage.value,
                    useCenter = false,
                    style = Stroke(
                        15.dp.toPx(),
                        cap = StrokeCap.Round,
                    )
                )
            }
            Text(
                text = (currentPercentage.value * taskCount).toInt().toString(),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                )
            )
        }
    }

}


@Preview
@Composable
fun CompletedStatusProgressBarPrev() {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    var currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) 20f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 500,
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    EmployeeLiveTrackingTheme {
        Box(modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(150.dp)) {
                drawArc(
                    color = Warning,
                    startAngle = -90f,
                    sweepAngle = 360 * 0.7f,
                    useCenter = false,
                    style = Stroke(
                        15.dp.toPx(),
                        cap = StrokeCap.Round,

                        )
                )
            }
            Text(text = (0.7f * 100).toInt().toString())
        }
    }
}