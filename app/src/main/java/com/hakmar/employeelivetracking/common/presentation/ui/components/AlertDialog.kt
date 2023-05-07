package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80

@Composable
fun CustomAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    postiveText: String,
    negativeText: String,
    onPositive: () -> Unit,
    onNegative: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = title, style = MaterialTheme.typography.titleSmall.copy(
                    color = Natural110,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    lineHeight = 24.sp
                )
            )
        },
        text = {
            Text(
                description, style = MaterialTheme.typography.bodyMedium.copy(
                    color = Natural80,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 22.sp
                )
            )
        },
        confirmButton = {
            Text(
                postiveText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    onPositive()
                })

        },
        dismissButton = {
            Text(
                negativeText,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    onNegative()
                })
        }
    )
}