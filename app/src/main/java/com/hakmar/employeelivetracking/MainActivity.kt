package com.hakmar.employeelivetracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.hakmar.employeelivetracking.common.presentation.graphs.RootNavigationGraph
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeLiveTrackingTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}