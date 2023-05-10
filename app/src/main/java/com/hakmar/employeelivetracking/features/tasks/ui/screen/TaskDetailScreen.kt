package com.hakmar.employeelivetracking.features.tasks.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.graphs.HomeDestination
import com.hakmar.employeelivetracking.common.presentation.ui.MainViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.components.CustomSnackbarVisuals
import com.hakmar.employeelivetracking.common.presentation.ui.components.FabState
import com.hakmar.employeelivetracking.common.presentation.ui.components.LocalSnackbarHostState
import com.hakmar.employeelivetracking.common.presentation.ui.components.TransparentHintTextField
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.notification.ui.OverflowMenu
import com.hakmar.employeelivetracking.features.tasks.ui.events.TaskDetailEvent
import com.hakmar.employeelivetracking.features.tasks.ui.events.TaskDetailFields
import com.hakmar.employeelivetracking.features.tasks.ui.viewmodel.TaskDetailViewModel
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.getContainerColor
import com.hakmar.employeelivetracking.util.getContentColor


class TaskDetailScreen(
    private val taskId: Int? = null
) : Screen {

    override val key: ScreenKey
        get() = "Task_Detail"

    @Composable
    override fun Content() {
        val mainViewModel = getViewModel<MainViewModel>()
        val viewModel = getViewModel<TaskDetailViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val snackbarHostState = LocalSnackbarHostState.current
        val title = stringResource(id = R.string.task_detail_title)
        LaunchedEffect(key1 = Unit) {
            mainViewModel.updateAppBar(
                AppBarState(
                    isNavigationButton = true,
                    navigationClick = { navigator.pop() },
                    title = title,
                    actions = {
                        OverflowMenu {
                            DropdownMenuItem(
                                onClick = { viewModel.onEvent(TaskDetailEvent.OnDeleteTask) },
                                text = {
                                    Text(
                                        text = stringResource(id = R.string.delete),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W400,
                                            lineHeight = 24.sp
                                        )
                                    )
                                })
                        }
                    }
                )
            )
            mainViewModel.updateFabState(
                FabState(
                    onClick = { viewModel.onEvent(TaskDetailEvent.OnSaveTask) },
                    icon = Icons.Default.Save
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
                            HomeDestination.Tasks.base -> {
                                navigator.replaceAll(TasksScreen(isRefresh = true))
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }
        LaunchedEffect(key1 = Unit) {
            viewModel.getTask(taskId)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 55.dp, topEnd = 55.dp)
                )
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            TransparentHintTextField(
                text = state.value.title,
                hint = stringResource(id = R.string.title_hint),
                onValueChange = {
                    viewModel.onEvent(TaskDetailEvent.OnTextChange(it, TaskDetailFields.Title))
                },
                isHintVisible = state.value.title.isEmpty(),
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp
                ),
                onFocusChange = {}
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            TransparentHintTextField(
                text = state.value.code,
                hint = stringResource(id = R.string.code_hint),
                onValueChange = {
                    viewModel.onEvent(TaskDetailEvent.OnTextChange(it, TaskDetailFields.Code))
                },
                onFocusChange = {},
                isHintVisible = state.value.code.isEmpty(),
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp
                ),
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            TransparentHintTextField(
                text = state.value.description,
                hint = stringResource(id = R.string.description_hint),
                onValueChange = {
                    viewModel.onEvent(
                        TaskDetailEvent.OnTextChange(
                            it,
                            TaskDetailFields.Description
                        )
                    )
                },
                onFocusChange = {},
                isHintVisible = state.value.description.isEmpty(),
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Natural80,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.fillMaxHeight()
            )

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun TaskDetailScreenPrev() {
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = "Bu bir başlık",
                hint = "",
                onValueChange = {

                },
                onFocusChange = {

                },
                isHintVisible = true,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = "5004",
                hint = "",
                onValueChange = {

                },
                onFocusChange = {

                },
                isHintVisible = true,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 24.sp
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue.",
                hint = "",
                onValueChange = {

                },
                onFocusChange = {

                },
                isHintVisible = true,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Natural80,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W200,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.fillMaxHeight()
            )

        }
    }
}
