package com.hakmar.employeelivetracking.features.store_detail.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.bs_store.ui.component.CircleIndicator

@Composable
fun StoreShiftCard(
    hour: String,
    minutue: String,
    second: String,
    indicatorValue: Int,
    maxIndicValue: Int,
    positiveIcon: ImageVector,
    isStopIconVisible: Boolean,
    onStopClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .widthIn(max = 400.dp)
            .padding(start = 24.dp, end = 24.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleIndicator(
                hour = hour,
                minitue = minutue,
                second = second,
                canvasSize = 150.dp,
                backgroundIndicatorStrokeWidth = 80f,
                foregroundIndicatorStrokeWidth = 80f,
                indicatorValue = indicatorValue,
                maxIndicatorValue = maxIndicValue,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { onPositiveClick() }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.primary, shape = CircleShape)
                    ) {
                        Icon(
                            modifier = Modifier.padding(MaterialTheme.spacing.small),
                            imageVector = positiveIcon,
                            contentDescription = "play"
                        )

                    }
                }
                if (isStopIconVisible)
                    IconButton(onClick = { onStopClick() }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red, shape = CircleShape)
                        ) {
                            Icon(
                                modifier = Modifier.padding(MaterialTheme.spacing.small),
                                imageVector = Icons.Default.Stop,
                                contentDescription = "stop"
                            )

                        }
                    }
            }
        }
    }
}

@Preview()
@Composable
fun StoreShiftCardPrev() {
    EmployeeLiveTrackingTheme {
        Card(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .height(300.dp)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.spacing.medium,
                        start = MaterialTheme.spacing.medium,
                    ), text = "Mağaza Mesaisi", style = MaterialTheme.typography.bodySmall
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CircleIndicator(
                    hour = "00",
                    minitue = "00",
                    second = "00",
                    canvasSize = 125.dp,
                    indicatorValue = 1,
                    maxIndicatorValue = 100,
                    backgroundIndicatorStrokeWidth = 50f,
                    foregroundIndicatorStrokeWidth = 50f
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.primary, shape = CircleShape)
                        ) {
                            Icon(
                                modifier = Modifier.padding(MaterialTheme.spacing.small),
                                imageVector = Icons.Default.Pause,
                                contentDescription = "play"
                            )

                        }
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red, shape = CircleShape)
                        ) {
                            Icon(
                                modifier = Modifier.padding(MaterialTheme.spacing.small),
                                imageVector = Icons.Default.Stop,
                                contentDescription = "stop"
                            )

                        }
                    }
                }

            }
        }
    }
}