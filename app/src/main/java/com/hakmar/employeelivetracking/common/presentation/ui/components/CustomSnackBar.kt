package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110

@Composable
fun CustomSnackBar(hostState: SnackbarHostState) {
    SnackbarHost(
        hostState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
    )
    { snackbarData: SnackbarData ->
        val customVisuals = snackbarData.visuals as? CustomSnackbarVisuals
        if (customVisuals != null) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = snackbarData.visuals.message,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                shape = RoundedCornerShape(12.dp),
                contentColor = customVisuals.contentColor,
                containerColor = customVisuals.containerColor,
            )
        } else {
            Snackbar(snackbarData = snackbarData, shape = RoundedCornerShape(12.dp))
        }
    }
}

data class CustomSnackbarVisuals(
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val containerColor: Color = Green40,
    val contentColor: Color = Natural110,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals

