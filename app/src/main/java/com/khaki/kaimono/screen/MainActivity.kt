package com.khaki.kaimono.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.khaki.kaimono.compose.TaskListContent
import com.khaki.kaimono.ui.theme.KaimonoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaimonoTheme {
                TaskListContent(
                    taskListUiState = TaskListUiState(),
                )
            }
        }
    }
}
