package com.khaki.kaimono.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khaki.kaimono.ui.theme.KaimonoTheme

@Composable
fun TaskListSeparator(
    isExpanded: Boolean,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    color = Color.LightGray
                )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            val angle: Float by animateFloatAsState(targetValue = if (isExpanded) 90f else 0f)

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .rotate(angle)
                    .padding(8.dp),
            )

            Text(
                text = "完了したタスク",
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Preview(
    name = "タスクセパレータ",
    showBackground = true,
)
@Composable
fun PreviewTaskListSpacer() {
    KaimonoTheme {
        TaskListSeparator(
            isExpanded = false,
        )
    }
}