package com.khaki.kaimono.compose

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khaki.kaimono.ui.theme.KaimonoTheme

@Composable
fun CheckboxLabel(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String?,
    isCheck: Boolean,
    paddingValue: PaddingValues = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
    onClick: () -> Unit = {},
    onClickEditButton: () -> Unit = {},
    onClickDeleteButton: () -> Unit = {},
) {

    val interactionSource = remember { MutableInteractionSource() }
    val labelAlpha = if (isCheck) 0.5f else 1f
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .alpha(labelAlpha)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
            )
            .padding(paddingValue),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Checkbox(
            checked = isCheck,
            onCheckedChange = { onClick() },
            interactionSource = interactionSource,
        )

        Column(
            modifier = Modifier
                .weight(
                    weight = 1f,
                    fill = true,
                ),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            val (annotatedTitle, annotatedSubTitle) = if (isCheck) {
                addStrikethroughAnnotation(title) to subTitle?.let { addStrikethroughAnnotation(it) }
            } else {
                AnnotatedString(title) to subTitle?.let { AnnotatedString(it) }
            }

            Text(
                modifier = Modifier,
                text = annotatedTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            if (annotatedSubTitle != null) {
                Text(
                    modifier = Modifier,
                    text = annotatedSubTitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Box {
            IconButton(
                onClick = {
                    isExpanded = !isExpanded
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = "編集")
                    },
                    onClick = {
                        onClickEditButton()
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "削除")
                    },
                    onClick = {
                        onClickDeleteButton()
                    }
                )
            }
        }


    }
}

@Composable
fun addStrikethroughAnnotation(
    text: String
): AnnotatedString {
    return buildAnnotatedString {
        append(text)
        addStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.LineThrough,
            ),
            start = 0,
            end = text.length
        )
    }
}

@Preview(
    name = "チェックボックス",
    showBackground = true,
)
@Composable
fun PreviewCheckboxLabel() {

    KaimonoTheme {
        Column {
            CheckboxLabel(
                modifier = Modifier.fillMaxWidth(),
                title = "CheckboxLabel",
                subTitle = null,
                isCheck = true,
            )

            CheckboxLabel(
                modifier = Modifier.fillMaxWidth(),
                title = "CheckboxLabel",
                subTitle = "subTitle",
                isCheck = false,
            )
        }

    }
}