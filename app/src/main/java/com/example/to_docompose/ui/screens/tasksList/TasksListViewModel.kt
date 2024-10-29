package com.example.to_docompose.ui.screens.tasksList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import com.example.to_docompose.ui.screens.tasksList.message.TaskMessage
import com.example.to_docompose.ui.screens.tasksList.message.TaskMessageBus
import com.example.to_docompose.utils.coroutines.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val applicationScope: ApplicationScope,
    private val toDoTasksRepository: ToDoTasksRepository,
    private val taskMessageBus: TaskMessageBus,
) : ViewModel() {

    companion object {
        private const val TAG = "TasksListViewModel"
    }

    init {
        Log.d(TAG, "init")
    }

    private val _searchAppBarState: MutableStateFlow<SearchAppBarState> = MutableStateFlow(SearchAppBarState.CLOSED)
    val searchAppBarState: StateFlow<SearchAppBarState> = _searchAppBarState

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: MutableStateFlow<String> = _searchQuery

    private val _deleteAllConfirmationDialogState: MutableStateFlow<TasksConfirmationDialogState> =
        MutableStateFlow(TasksConfirmationDialogState.Hidden)
    val deleteAllConfirmationDialogState: StateFlow<TasksConfirmationDialogState> =
        _deleteAllConfirmationDialogState

    val tasks: StateFlow<List<ToDoTask>> =
        searchQuery
            .flatMapLatest(toDoTasksRepository::searchTasks)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val taskMessage: StateFlow<TaskMessage?> =
        taskMessageBus.message

    fun onTaskMessageHandled() {
        taskMessageBus.onMessageHandled()
    }

    fun deleteTasks(tasks: List<ToDoTask>) {
        applicationScope.launch {
            val tasksIds = tasks.map(ToDoTask::id)
            toDoTasksRepository.deleteTasks(tasksIds)
            taskMessageBus.sendMessage(
                TaskMessage.TasksDeleted(tasks)
            )
        }
    }

    fun restoreTasks(tasks: List<ToDoTask>) {
        applicationScope.launch {
            toDoTasksRepository.addTasks(tasks)
            taskMessageBus.sendMessage(
                TaskMessage.TasksRestored(tasks)
            )
        }
    }

    fun onOpenSearchClick() {
        _searchAppBarState.value = SearchAppBarState.OPENED
    }

    fun onCloseSearchClick() {
        if (_searchQuery.value.isNotEmpty()) {
            _searchQuery.value = ""
        } else {
            _searchAppBarState.value = SearchAppBarState.CLOSED
        }
    }

    fun searchTasks(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

    fun showDeleteAllConfirmationDialog(tasks: List<ToDoTask>) {
        _deleteAllConfirmationDialogState.value = TasksConfirmationDialogState.Shown(tasks)
    }

    fun hideDeleteAllConfirmationDialog() {
        _deleteAllConfirmationDialogState.value = TasksConfirmationDialogState.Hidden
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}

sealed interface TasksConfirmationDialogState {

    data class Shown(
        val tasks: List<ToDoTask>,
    ) : TasksConfirmationDialogState

    data object Hidden : TasksConfirmationDialogState
}
