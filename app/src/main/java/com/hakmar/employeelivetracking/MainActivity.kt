@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.hakmar.employeelivetracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import com.hakmar.employeelivetracking.common.presentation.graphs.EmployeeLiveTrackingApp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeLiveTrackingTheme {
                EmployeeLiveTrackingApp(
                    navController = rememberNavController(),
                    windowSizeClass = calculateWindowSizeClass(activity = this)
                )
            }
        }
    }
}