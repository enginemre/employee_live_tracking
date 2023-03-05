package com.hakmar.employeelivetracking.common.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun PMStoreScreen(
    /* onBackPressed : () -> Unit,
     onAppBarConfig : (AppBarState) -> Unit*/
) {
/*    LaunchedEffect(key1 = true){
        onAppBarConfig(
            AppBarState(
                isNavigationButton = true,
                title = "Pazarlama Müdürleri",
                navigationClick = onBackPressed
            )
        )
    }*/
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = "PM Store Screen", style = MaterialTheme.typography.headlineSmall)

    }
}