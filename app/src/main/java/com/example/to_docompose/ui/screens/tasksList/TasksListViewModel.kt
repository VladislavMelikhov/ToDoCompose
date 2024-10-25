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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    val allTasks: StateFlow<List<ToDoTask>> =
        toDoTasksRepository
            .getAllTasks()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val taskMessage: StateFlow<TaskMessage?> =
        taskMessageBus.message

    fun onTaskMessageHandled() {
        taskMessageBus.onMessageHandled()
    }

    fun restoreTask(task: ToDoTask) {
        applicationScope.launch {
            toDoTasksRepository.addTask(task)
            taskMessageBus.sendMessage(
                TaskMessage.TaskRestored(taskTitle = task.title)
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

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}
