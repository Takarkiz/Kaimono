package com.khaki.kaimono.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khaki.kaimono.ui.theme.KaimonoTheme

@Composable
fun ConfirmButtons(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
    onClickCancel: () -> Unit = {},
    onClickConfirm: () -> Unit = {},
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {

        OutlinedButton(
            onClick = onClickCancel
        ) {
            Text(text = "キャンセル")
        }

        Spacer(modifier = Modifier.width(4.dp))

        val confirmText = if (isEditing) "更新" else "作成"

        Button(
            onClick = onClickConfirm
        ) {
            Text(text = confirmText)
        }
    }
}

@Preview
@Composable
fun PreviewConfirmButtons() {

    KaimonoTheme {
        ConfirmButtons(
            isEditing = true
        )
    }
}