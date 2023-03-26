package com.hakmar.employeelivetracking.features.tasks.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.FabState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.tasks.domain.Task
import com.hakmar.employeelivetracking.features.tasks.ui.component.TaskItem

@OptIn(ExperimentalFoundationApi::class)

class TasksScreen : Screen {

    override val key: ScreenKey
        get() = HomeDestination.Tasks.base


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainViewModel = getViewModel<MainViewModel>()
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    isNavigationButton = false,
                    title = "Görevler"
                )
            )
            mainViewModel.updateFabState(
                FabState(
                    onClick = { navigator.push(TaskDetailScreen()) },
                    icon = Icons.Default.Add
                )
            )
        }
        val list = listOf<Task>(
            Task(
                color = Color(0xff32fd45),
                title = "Bu bir görev",
                description = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.",
                storeCode = "5004"
            ),
            Task(
                color = Color(0xff9dfcff),
                title = "Bu bir görev",
                description = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor." +
                        " Aenean massa. Cum sociis natoque penatibusmus. ",
                storeCode = "5004"
            ),
            Task(
                color = Color(0xffffaa95),
                title = "Bu bir görev",
                description = "Lorem ipsum olor." +
                        " Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. ",
                storeCode = "5004"
            )
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(list) {
                TaskItem(
                    color = it.color,
                    storeCode = it.storeCode,
                    title = it.title,
                    description = it.description
                )
            }
        }
    }

}