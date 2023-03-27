package com.hakmar.employeelivetracking.features.onboarding.ui.component

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hakmar.employeelivetracking.common.presentation.ui.theme.*

@Composable
fun PageContent(
    title: String,
    description: String,
    @RawRes rawRes: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(rawRes)
            )
            LottieAnimation(
                modifier = Modifier.fillMaxHeight(0.4f),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                    ),
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.large,
                            vertical = MaterialTheme.spacing.small
                        ),
                    text = title, style = MaterialTheme.typography.bodyMedium.copy(
                        color = Natural100,
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.W600
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.large),
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Natural80,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = CircleShape
                        )
                        .size(70.dp),
                        onClick = { onClick() }) {
                        Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "")
                    }
                }

            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PageContentPrev() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(com.hakmar.employeelivetracking.R.raw.success)
            )
            LottieAnimation(
                modifier = Modifier.fillMaxHeight(0.4f),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                    ),
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.large,
                            vertical = MaterialTheme.spacing.small
                        ),
                    text = "description", style = MaterialTheme.typography.bodyMedium.copy(
                        color = Natural100,
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.W600
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.large),
                    text = "smdlfdsamkdfsakmmdklafskmlfdkdafskmdsfakmladksflmasdkfmdskfam",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Natural80,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = CircleShape
                        )
                        .size(70.dp),
                        onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = ""
                        )
                    }
                }


            }
        }
    }

}