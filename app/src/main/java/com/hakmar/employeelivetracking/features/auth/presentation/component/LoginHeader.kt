package com.hakmar.employeelivetracking.features.auth.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@Composable
fun LoginHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.large),
            text = stringResource(id = R.string.welcome_label),
            style = MaterialTheme.typography.headlineLarge,
            color = Natural110,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.large,
                    end = MaterialTheme.spacing.large,
                    top = MaterialTheme.spacing.medium
                ),
            text = stringResource(id = R.string.welcome_header),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.W300,
                color = Natural80,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal
            ),
            textAlign = TextAlign.Center
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun LoginHeaderPreview() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.large),
                text = "Hoş Geldiniz",
                style = MaterialTheme.typography.headlineLarge,
                color = Natural110,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large,
                        top = MaterialTheme.spacing.medium
                    ),
                text = "Hemen giriş yapın ve görevlerinizi tamamlamaya başlayın.",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W300,
                    color = Natural80,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal
                ),
                textAlign = TextAlign.Center
            )
        }

    }

}