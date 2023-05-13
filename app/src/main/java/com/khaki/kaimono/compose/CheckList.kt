package com.khaki.kaimono.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.ui.theme.KaimonoTheme

@Composable
fun CheckList(
    modifier: Modifier = Modifier,
    tasks: List<TaskUiModel>,
    onClick: (Int) -> Unit = {},
) {

    var isExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            items = tasks.filter { !it.isDone },
            key = { it.id },
        ) { task ->
            CheckboxLabel(
                modifier = Modifier.fillMaxWidth(),
                title = task.title,
                subTitle = task.description,
                isCheck = task.isDone,
            ) {
                onClick(task.id)
            }
        }

        item {
            TaskListSeparator(isExpanded = isExpanded) {
                isExpanded = !isExpanded
            }
        }


        if (isExpanded) {
            items(
                items = tasks.filter { it.isDone },
                key = { it.id },
            ) { task ->
                CheckboxLabel(
                    modifier = Modifier.fillMaxWidth(),
                    title = task.title,
                    subTitle = task.description,
                    isCheck = task.isDone,
                ) {
                    onClick(task.id)
                }
            }
        }
    }
}

@Preview(
    name = "CheckList",
    showBackground = true,
)
@Composable
fun PreviewCheckList() {
    KaimonoTheme {
        CheckList(
            tasks = listOf(
                TaskUiModel(
                    id = 1,
                    title = "title1",
                    description = "description1",
                    isDone = false,
                ),
                TaskUiModel(
                    id = 2,
                    title = "title2",
                    description = "description2",
                    isDone = true,
                ),
                TaskUiModel(
                    id = 3,
                    title = "title3",
                    isDone = false,
                ),
                TaskUiModel(
                    id = 4,
                    title = "title4",
                    description = "description4",
                    isDone = true,
                ),
            )
        )
    }
}