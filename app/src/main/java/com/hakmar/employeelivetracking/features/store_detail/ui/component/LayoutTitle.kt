package com.hakmar.employeelivetracking.features.store_detail.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing


@Composable
fun LayoutTitle(
    title: String,
    link: String,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .padding(
                start = MaterialTheme.spacing.large,
                end = MaterialTheme.spacing.large,
                top = MaterialTheme.spacing.large
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title, style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 24.sp
            )
        )
        Text(
            text = link, style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 14.sp,
                color = Natural80,
                fontWeight = FontWeight.W400
            )
        )
    }
}