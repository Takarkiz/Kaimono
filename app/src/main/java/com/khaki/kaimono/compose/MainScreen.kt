package com.khaki.kaimono.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.screen.TaskListUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListContent(
    taskListUiState: TaskListUiState,
) {

    val taskList by taskListUiState.tasks.collectAsState(initial = listOf())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        },
    ) {
        CheckList(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .padding(it),
            tasks = taskList,
        )
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
    val taskListUiState = TaskListUiState(
        tasks = remember { flowOf(taskList) },
    )
    TaskListContent(
        taskListUiState = taskListUiState,
    )
}