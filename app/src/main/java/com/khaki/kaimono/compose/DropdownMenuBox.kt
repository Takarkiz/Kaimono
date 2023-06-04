package com.khaki.kaimono.compose

import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khaki.kaimono.compose.uimodel.TaskUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    location: TaskUiModel.Location?,
    menus: List<TaskUiModel.Location>,
    onExpandMenu: (Boolean) -> Unit,
    onSelectMenu: (TaskUiModel.Location) -> Unit,
) {

    ExposedDropdownMenuBox(
        modifier = modifier
            .clickable {
                onExpandMenu(true)
            },
        expanded = expanded,
        onExpandedChange = {
            onExpandMenu(true)
        }
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = location?.name ?: "未指定",
            readOnly = true,
            singleLine = true,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandMenu(false)
            }
        ) {
            menus.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onExpandMenu(false)
                        onSelectMenu(selectionOption)
                    },
                    text = {
                        Text(text = selectionOption.name)
                    }
                )
            }
        }

    }
}