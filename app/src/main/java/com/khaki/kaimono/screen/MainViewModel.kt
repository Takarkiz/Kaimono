package com.khaki.kaimono.screen

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.kaimono.compose.uimodel.TaskUiModel
import com.khaki.kaimono.db.Task
import com.khaki.kaimono.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class TaskListViewModelState(
    val isLoading: Boolean = false,
    val taskList: List<Task> = listOf(),
) {

    fun toUiState(): TaskListUiState {
        return TaskListUiState(
            isLoading = isLoading,
            tasks = taskList.map { task -> TaskUiModel(task) },
        )
    }
}

/**
 * MainActivityのViewModel
 */
class MainViewModel(
    private val repository: TaskRepository
) : ViewModel(), DefaultLifecycleObserver {

    private val viewModelState = MutableStateFlow(TaskListViewModelState())

    /**
     * UIの状態を流すFlow
     */
    val uiState: StateFlow<TaskListUiState>
        get() = viewModelState
            .map { it.toUiState() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = TaskListUiState(),
            )

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModelScope.launch {
            repository.tasks
                .onStart {
                    viewModelState.update { it.copy(isLoading = true) }
                }
                .onCompletion {
                    viewModelState.update { it.copy(isLoading = false) }
                }
                .catch { cause: Throwable ->
                    TODO("エラー処理")
                }
                .onEach { tasks ->
                    viewModelState.update {
                        it.copy(
                            taskList = tasks,
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }

    /**
     * UIのアクション処理を実行する
     */
    fun dispatch(action: TaskListActions) {
        when (action) {
            is TaskListActions.DidTapOpenDialog -> {
                dispatchOpenDialog()
            }

            is TaskListActions.DidTapTask -> {
                dispatchSwitchTask(action.id)
            }

            is TaskListActions.DidTapAddTask -> {
                dispatchAddTask(action.task)
            }

            is TaskListActions.DidTapEditTask -> {
                dispatchEditTask(action.task)
            }

            is TaskListActions.DidTapDeleteTask -> {
                dispatchDeleteTask(action.id)
            }
        }
    }

    // Private

    private fun dispatchOpenDialog() {

    }

    private fun dispatchSwitchTask(taskId: Int) {
        viewModelScope.launch {
            val task = repository.findById(taskId)
            repository.update(task.copy(isDone = !task.isDone))
        }
    }

    private fun dispatchAddTask(task: TaskUiModel) {
        val newlyTask = Task(
            uid = (0..Int.MAX_VALUE).random(),
            title = task.title,
            subTitle = task.description ?: "",
            isDone = false,
            isImportant = false,
            location = "",
            dueDate = "",
            createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
        )
        viewModelScope.launch {
            repository.insert(
                newlyTask
            )
        }
    }

    private fun dispatchEditTask(task: TaskUiModel) {
        val prevTask = viewModelState.value.taskList.find { it.uid == task.id } ?: return
        viewModelScope.launch {
            repository.update(
                prevTask.copy(
                    title = task.title,
                    subTitle = task.description ?: "",
                    isDone = task.isDone,
                )
            )
        }
    }

    private fun dispatchDeleteTask(taskId: Int) {
        viewModelScope.launch {
            val task = repository.findById(taskId)
            repository.delete(task)
        }
    }

}