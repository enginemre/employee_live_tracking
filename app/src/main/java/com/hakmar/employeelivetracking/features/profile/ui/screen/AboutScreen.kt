package com.hakmar.employeelivetracking.features.profile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.ProfileDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

class AboutScreen : Screen {

    override val key: ScreenKey
        get() = ProfileDestination.AboutUs.base

    @Composable
    override fun Content() {
        val mainViewModel = getViewModel<MainViewModel>()
        val title = stringResource(id = R.string.about_us)
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    title = title,
                    isNavigationButton = true,
                    navigationClick = {
                        navigator.pop()
                    }
                )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = MaterialTheme.spacing.large)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                )
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.spacing.small,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    ),
                text = stringResource(id = R.string.our_history),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Natural110,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = MaterialTheme.spacing.small,
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    ),
                text = stringResource(id = R.string.our_history_content),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Natural80,
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.W400
                )
            )
        }

    }
}