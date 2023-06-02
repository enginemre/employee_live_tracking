package com.hakmar.employeelivetracking.features.profile.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural100
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    name: String,
    mail: String
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name, style = MaterialTheme.typography.titleSmall.copy(
                color = Natural100,
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 32.sp,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = mail, style = MaterialTheme.typography.labelSmall.copy(
                color = Natural80,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfileHeaderPrev() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Emre Engin", style = MaterialTheme.typography.titleSmall.copy(
                color = Natural100,
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 32.sp,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "emrengin@yaani.com", style = MaterialTheme.typography.labelSmall.copy(
                color = Natural80,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )
        )

    }
}