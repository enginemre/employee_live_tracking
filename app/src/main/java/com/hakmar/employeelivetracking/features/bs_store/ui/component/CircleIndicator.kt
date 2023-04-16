package com.hakmar.employeelivetracking.features.bs_store.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@Composable
fun CircleIndicator(
    canvasSize: Dp = 200.dp,
    indicatorValue: Int = 1,
    maxIndicatorValue: Int = 36000,
    backgroundIndicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
    backgroundIndicatorStrokeWidth: Float = 100f,
    foregroundIndicatorColor: Color = MaterialTheme.colors.primary,
    foregroundIndicatorStrokeWidth: Float = 100f,
    hour: String,
    minitue: String,
    second: String
) {
    val animatedIndicatorValue = remember {
        Animatable(initialValue = 1f)
    }
    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
        indicatorValue
    } else {
        maxIndicatorValue
    }
    val percentage = (animatedIndicatorValue.value / maxIndicatorValue) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000)
    )

    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue.animateTo(allowedIndicatorValue.toFloat())
    }

    Column(
        modifier = Modifier
            .padding(top = MaterialTheme.spacing.extraLarge)
            .size(canvasSize)
            .drawBehind {
                backgroundIndicator(
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth,
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StopWatchClock(hour = hour, minitue = minitue, second = second)
    }
}

fun DrawScope.backgroundIndicator(
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),

        )

}


fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),

        )

}


@Preview
@Composable
fun CircleIndicatorPreview() {
    EmployeeLiveTrackingTheme {

    }
}