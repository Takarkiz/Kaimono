package com.khaki.kaimono.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import com.khaki.kaimono.compose.TaskListContent
import com.khaki.kaimono.ui.theme.KaimonoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

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
