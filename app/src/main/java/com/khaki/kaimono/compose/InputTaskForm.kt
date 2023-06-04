package com.khaki.kaimono.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.ui.theme.KaimonoTheme

@Composable
fun InputTaskForm(
    modifier: Modifier = Modifier,
    editingTask: TaskUiModel?,
    locationList: List<TaskUiModel.Location>,
    editingMode: Boolean,
    onEditTask: (TaskUiModel) -> Unit = {},
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {

    var locationDropdown: Boolean by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        val title = if (editingMode) "アイテム編集" else "新規作成"

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
            ),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "アイテム名",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )

            OutlinedTextField(
                value = editingTask?.title ?: "",
                singleLine = true,
                placeholder = {
                    Text(
                        text = "アイテム名",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                    )
                },
                onValueChange = {
                    val currentTask = editingTask ?: TaskUiModel.empty()
                    onEditTask(currentTask.copy(title = it))
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "場所名",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )


            DropdownMenuBox(
                expanded = locationDropdown,
                location = editingTask?.location,
                menus = locationList,
                onExpandMenu = {
                    locationDropdown = it
                },
                onSelectMenu = {
                    val currentTask = editingTask ?: TaskUiModel.empty()
                    onEditTask(currentTask.copy(location = it))
                },
            )
        }

        ConfirmButtons(
            modifier = Modifier.fillMaxWidth(),
            isEditing = editingMode,
            onClickConfirm = {
                onConfirm()
            },
            onClickCancel = onCancel,
        )

    }
}

@Preview(
    name = "アイテム入力フォーム",
    showBackground = true,
)
@Composable
fun PreviewInputTaskForm_new() {

    KaimonoTheme {
        InputTaskForm(
            editingTask = null,
            editingMode = true,
            locationList = listOf(),
        )
    }
}

@Preview(
    name = "アイテム入力フォーム",
    showBackground = true,
)
@Composable
fun PreviewInputTaskForm_edit() {

    KaimonoTheme {
        InputTaskForm(
            editingTask = TaskUiModel(
                id = 0,
                title = "ああああああ",
                description = null,
                isDone = false,
                location = TaskUiModel.Location(1, "スーパー"),
            ),
            editingMode = false,
            locationList = listOf(
                TaskUiModel.Location(0, "家"),
                TaskUiModel.Location(1, "スーパー"),
                TaskUiModel.Location(2, "コンビニ"),
            )
        )
    }
}