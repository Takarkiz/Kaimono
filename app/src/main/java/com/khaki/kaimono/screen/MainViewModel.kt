package com.khaki.kaimono.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


private data class TaskListViewModelState(
    val isLoading: Boolean = false,
    val taskList: List<TaskUiModel> = listOf(),
) {

    fun toUiState(): TaskListUiState {
        return TaskListUiState()
    }
}

class MainViewModel(
    application: Application,
): ViewModel() {

    private val repository = TaskRepository(application)

    val uiState: StateFlow<TaskListUiState> get() = _uiState
    private val _uiState = MutableStateFlow(TaskListUiState())
}