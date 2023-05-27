package com.khaki.kaimono.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.screen.TaskListActions
import com.khaki.kaimono.screen.TaskListUiState
import com.khaki.kaimono.ui.theme.KaimonoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    taskListUiState: TaskListUiState,
    dispatch: (TaskListActions) -> Unit = {},
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val bottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {
            if (it == SheetValue.Hidden) {
                dispatch(TaskListActions.DismissDialog)
            }
            true
        }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState,
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(taskListUiState.isOpenBottomSheet) {
        if (taskListUiState.isOpenBottomSheet) {
            scaffoldState.bottomSheetState.expand()
        } else {
            scaffoldState.bottomSheetState.hide()
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "買い物リスト",
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        sheetContent = {
            InputTaskForm(
                editingTask = taskListUiState.editingTask,
                editingMode = taskListUiState.editingMode,
                onEditTask = {
                    dispatch(TaskListActions.InputEditingTask(it))
                },
                onConfirm = {
                    dispatch(TaskListActions.DidTapConfirmTask)
                },
                onCancel = {
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                },
            )
        },
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {

            CheckList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                tasks = taskListUiState.tasks,
                onClick = {
                    dispatch(TaskListActions.DidTapTask(it))
                },
                onClickEdit = {
                    dispatch(TaskListActions.DidTapStartEditTask(it))
                },
                onClickDelete = {
                    dispatch(TaskListActions.DidTapDeleteTask(it))
                }
            )

            if (taskListUiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    dispatch(TaskListActions.DidTapFAB)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTaskListContent() {
    val taskList = listOf(
        TaskUiModel(
            id = 1,
            title = "牛乳",
            isDone = false,
        ),
        TaskUiModel(
            id = 2,
            title = "パン",
            isDone = true,
        ),
        TaskUiModel(
            id = 3,
            title = "卵",
            isDone = false,
        ),
    )

    KaimonoTheme {
        TaskListScreen(
            taskListUiState = TaskListUiState(
                tasks = taskList,
            ),
        )
    }
}