package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors

@Composable
fun CustomSnackBar(hostState: SnackbarHostState) {
    SnackbarHost(
        hostState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
    ) { data ->
        Snackbar(
            modifier = Modifier.padding(16.dp),
            content = {
                Text(
                    text = data.visuals.message,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            containerColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onSecondary,
            shape = RoundedCornerShape(12.dp)
        )
    }
}