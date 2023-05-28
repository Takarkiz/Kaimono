package com.khaki.kaimono.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    editingMode: Boolean,
    onEditTask: (TaskUiModel) -> Unit = {},
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {

//    var taskName: String by remember {
//        mutableStateOf(editingTask?.title ?: "")
//    }

//    var locationDropdown: Boolean by remember {
//        mutableStateOf(false)
//    }
//
//    var selectedLocation: String? by remember {
//        mutableStateOf(editingTask?.location)
//    }

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
                val currentTask = editingTask ?: TaskUiModel(
                    id = 0,
                    title = "",
                    description = null,
                    isDone = false,
                    location = null,
                )
                onEditTask(currentTask.copy(title = it))
            }
        )

        ConfirmButtons(
            modifier = Modifier.fillMaxWidth(),
            isEditing = editingMode,
            onClickConfirm = {
                onConfirm()
            },
            onClickCancel = onCancel,
        )

//        ExposedDropdownMenuBox(
//            expanded = locationDropdown,
//            onExpandedChange = {
//                locationDropdown = it
//            }
//        ) {
//            TextField(
//                value = selectedLocation ?: "未指定",
//                readOnly = true,
//                onValueChange = {
//                    selectedLocation = it
//                },
//                label = {
//                    Text(
//                        text = "場所",
//                        style = MaterialTheme.typography.body1,
//                    )
//                },
//                colors = ExposedDropdownMenuDefaults.textFieldColors(),
//            )
//
//            ExposedDropdownMenu(
//                expanded = locationDropdown,
//                onDismissRequest = {
//                    locationDropdown = false
//                }
//            ) {
//                options.forEach { selectionOption ->
//                    DropdownMenuItem(
//                        onClick = {
//                            selectedOptionText = selectionOption
//                            expanded = false
//                        }
//                    ){
//                        Text(text = selectionOption)
//                    }
//                }
//            }
//    }

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
                location = null,
            ),
            editingMode = false,
        )
    }
}