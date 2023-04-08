package com.hakmar.employeelivetracking.features.tasks.ui.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.graphs.TaskDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackbarVisuals
import com.hakmar.employeelivetracking.common.presentation.ui.components.FabState
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.tasks.ui.component.TaskItem
import com.hakmar.employeelivetracking.features.tasks.ui.events.TaskEvent
import com.hakmar.employeelivetracking.features.tasks.ui.viewmodel.TaskViewModel
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor

@OptIn(ExperimentalFoundationApi::class)

class TasksScreen(var isRefresh: Boolean = false) : Screen {

    override val key: ScreenKey
        get() = HomeDestination.Tasks.base


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainViewModel = getViewModel<MainViewModel>()
        val viewModel = getViewModel<TaskViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)
        val title = stringResource(id = R.string.tasks)
        val snackbarHostState = LocalSnackbarHostState.current
        val context = LocalContext.current
        LaunchedEffect(key1 = Unit) {
            if (isRefresh) {
                viewModel.onEvent(TaskEvent.OnRefreshTask)
                isRefresh = false
            }
            mainViewModel.updateAppBar(
                AppBarState(
                    isNavigationButton = false,
                    title = title
                )
            )
            mainViewModel.updateFabState(
                FabState(
                    onClick = { viewModel.onEvent(TaskEvent.OnAddTask) },
                    icon = Icons.Default.Add
                )
            )
        }
        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                message = event.message.asString(context),
                                contentColor = getContentColor(event.type),
                                containerColor = getContainerColor(event.type)
                            )
                        )
                    }
                    is UiEvent.Navigate<*> -> {
                        when (event.route) {
                            TaskDestination.TaskDetail.base -> {
                                navigator.push(TaskDetailScreen(event.data as Int))
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(TaskEvent.OnRefreshTask) }) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.taskList) {
                    TaskItem(
                        color = it.color!!,
                        storeCode = it.storeCode,
                        title = it.title,
                        description = it.description
                    ) {
                        viewModel.onEvent(TaskEvent.OnClickTask(it))
                    }
                }
            }
        }

    }

}