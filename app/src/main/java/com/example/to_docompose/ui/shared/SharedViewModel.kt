package com.example.to_docompose.ui.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import com.example.to_docompose.ui.screens.tasksList.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoTasksRepository: ToDoTasksRepository,
) : ViewModel() {

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> =
        mutableStateOf("")

    val allTasks: StateFlow<List<ToDoTask>> =
        toDoTasksRepository
            .getAllTasks()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedTaskId: MutableStateFlow<Int> =
        MutableStateFlow(-1)

    val selectedTask: StateFlow<ToDoTask?> =
        _selectedTaskId
            .flatMapLatest { taskId ->
                if (taskId > 0) {
                    toDoTasksRepository.getTask(taskId)
                } else {
                    flowOf(null)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun selectTask(taskId: Int) {
        _selectedTaskId.value = taskId
    }
}
