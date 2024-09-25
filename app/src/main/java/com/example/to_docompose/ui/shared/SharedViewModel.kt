package com.example.to_docompose.ui.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import com.example.to_docompose.ui.screens.tasksList.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoTasksRepository: ToDoTasksRepository,
) : ViewModel() {

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> =
        mutableStateOf("")

    private val _allTasks: MutableStateFlow<List<ToDoTask>> = MutableStateFlow(emptyList())
    val allTasks: StateFlow<List<ToDoTask>> = _allTasks

    init {
        toDoTasksRepository
            .getAllTasks()
            .onEach { tasks -> _allTasks.value = tasks }
            .launchIn(viewModelScope)
    }
}
