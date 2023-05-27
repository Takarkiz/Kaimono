package com.khaki.kaimono.screen.task_list

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.room.Room
import com.khaki.kaimono.compose.TaskListScreen
import com.khaki.kaimono.db.database.AppDatabase
import com.khaki.kaimono.repositoryImpl.TaskRepositoryImpl
import com.khaki.kaimono.ui.theme.KaimonoTheme

class MainActivity : ComponentActivity() {

    private val extraKeyId = object : CreationExtras.Key<Application> {}
    private val viewModel: MainViewModel by viewModels(
        extrasProducer = {
            MutableCreationExtras(defaultViewModelCreationExtras).apply {
                set(extraKeyId, application)
            }
        },
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    // TODO: ActivityはRoomことを知りたくないので、DIで注入できるようにする
                    val db = Room.databaseBuilder(
                        this@MainActivity,
                        AppDatabase::class.java, "database-name"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    val taskDao = db.taskDao()
                    return MainViewModel(
                        repository = TaskRepositoryImpl(taskDao),
                    ) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)

        setContent {

            val uiState by viewModel.uiState.collectAsState()

            KaimonoTheme {
                TaskListScreen(
                    taskListUiState = uiState,
                    dispatch = viewModel::dispatch,
                )
            }
        }
    }
}