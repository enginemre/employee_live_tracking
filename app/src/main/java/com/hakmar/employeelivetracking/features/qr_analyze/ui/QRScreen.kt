package com.hakmar.employeelivetracking.features.qr_analyze.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hakmar.employeelivetracking.common.presentation.ui.components.LargeButton
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@Composable
fun QRScreen(
    viewModel: QRAnalysisViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.large),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.value.result, style = MaterialTheme.typography.titleSmall,
        )
        LargeButton(text = "Scan", onClick = viewModel::startScanning)
    }
}