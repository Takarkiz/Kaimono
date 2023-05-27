package com.khaki.kaimono.screen.task_list

import android.util.Log
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
    val isOpenBottomSheet: Boolean = false,
    val editingTask: TaskUiModel? = null,
    val editingMode: Boolean = false,
) {

    fun toUiState(): TaskListUiState {
        return TaskListUiState(
            isLoading = isLoading,
            tasks = taskList.map { task -> TaskUiModel.fromEntity(task) },
            isOpenBottomSheet = isOpenBottomSheet,
            editingTask = editingTask,
            editingMode = editingMode,
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
                .catch { cause: Throwable ->
                    // エラー処理記述
                    Log.e("MainViewModel", "タスク取得エラー", cause)
                    viewModelState.update { it.copy(isLoading = false) }
                }
                .onEach { tasks ->
                    viewModelState.update {
                        it.copy(
                            taskList = tasks,
                            isLoading = false,
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
            is TaskListActions.DidTapFAB -> {
                dispatchFABAction()
            }

            is TaskListActions.DismissDialog -> {
                dispatchCloseDialog()
            }

            is TaskListActions.DidTapTask -> {
                dispatchSwitchTask(action.id)
            }

            is TaskListActions.DidTapConfirmTask -> {
                if (viewModelState.value.editingMode) {
                    dispatchUpdateTask()
                } else {
                    dispatchAddTask()
                }
            }

            is TaskListActions.DidTapStartEditTask -> {
                dispatchStartEdit(action.id)
            }

            is TaskListActions.CancelEditTask -> {
                dispatchCancelEdit()
            }

            is TaskListActions.DidTapDeleteTask -> {
                dispatchDeleteTask(action.id)
            }

            is TaskListActions.InputEditingTask -> {
                viewModelState.update {
                    it.copy(
                        editingTask = action.task,
                    )
                }
            }
        }
    }

    // Private

    private fun dispatchFABAction() {
        viewModelState.update {
            it.copy(
                isOpenBottomSheet = true,
                editingTask = null,
                editingMode = false,
            )
        }
    }

    private fun dispatchCloseDialog() {
        viewModelState.update {
            it.copy(
                isOpenBottomSheet = false,
                editingTask = null,
            )
        }
    }

    private fun dispatchSwitchTask(taskId: Int) {
        viewModelScope.launch {
            val task = repository.findById(taskId)
            repository.update(task.copy(isDone = !task.isDone))
        }
    }

    private fun dispatchAddTask() {
        val task = viewModelState.value.editingTask ?: return
        val newlyTask = Task(
            uid = (0..Int.MAX_VALUE).random(),
            title = task.title,
            subTitle = task.description,
            isDone = false,
            isImportant = null,
            locationId = null,
            dueDate = null,
            createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
        )
        viewModelScope.launch {
            repository.insert(
                newlyTask
            )
        }
        viewModelState.update {
            it.copy(
                isOpenBottomSheet = false,
                editingTask = null,
                editingMode = false,
            )
        }
    }

    private fun dispatchStartEdit(taskId: Int) {
        val task = viewModelState.value.taskList.find { it.uid == taskId } ?: return
        viewModelState.update {
            it.copy(
                isOpenBottomSheet = true,
                editingTask = TaskUiModel.fromEntity(task),
                editingMode = true,
            )
        }
    }

    private fun dispatchUpdateTask() {
        val editedTask = viewModelState.value.editingTask ?: return
        val prevTask = viewModelState.value.taskList.find { it.uid == editedTask.id } ?: return
        viewModelScope.launch {
            repository.update(
                prevTask.copy(
                    title = editedTask.title,
                    subTitle = editedTask.description,
                    isDone = editedTask.isDone,
                )
            )
        }

        viewModelState.update {
            it.copy(
                isOpenBottomSheet = false,
                editingTask = null,
                editingMode = false,
            )
        }
    }

    private fun dispatchCancelEdit() {
        viewModelState.update {
            it.copy(
                isOpenBottomSheet = false,
                editingTask = null,
                editingMode = false,
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