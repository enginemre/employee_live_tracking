package com.hakmar.employeelivetracking.common.presentation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.features.tasks.ui.TaskDetailScreen
import com.hakmar.employeelivetracking.features.tasks.ui.TasksScreen

@Composable
fun TasksGraph(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel,
) {
    val routerTasks = RouterTasks(navController)
    NavHost(
        navController = navController,
        route = HomeDestination.Tasks.base,
        startDestination = TaskDestination.Home.base
    ) {
        composable(route = TaskDestination.Home.base) {
            TasksScreen(
                onTaskDetail = { routerTasks.goTaskDetail(it) },
                onFabState = { mainViewModel.updateFabState(it) },
                onAppBarConfig = { mainViewModel.updateAppBar(it) },
            )
        }
        composable(
            route = TaskDestination.TaskDetail.path,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) {
            TaskDetailScreen(
                onFabState = { mainViewModel.updateFabState(it) },
                onAppBarConfig = { mainViewModel.updateAppBar(it) },
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}

private class RouterTasks(
    val navController: NavController
) {
    fun goTaskDetail(taskId: String) {
        navController.navigate("${TaskDestination.TaskDetail.base}/${taskId}")
    }
}

sealed interface TaskDestination {
    val base: String
    val path: String

    object Home : TaskDestination {
        override val base: String = "/"
        override val path: String = base
    }

    object TaskDetail : TaskDestination {
        override val base: String = "/task"
        override val path: String = "${base}/{taskId}"
    }
}