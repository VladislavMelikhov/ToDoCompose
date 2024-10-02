package com.example.to_docompose.ui.shared

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import com.example.to_docompose.ui.screens.tasksList.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoTasksRepository: ToDoTasksRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "SharedViewModel"
    }

    init {
        Log.d(TAG, "init")
    }

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> =
        mutableStateOf("")

    val allTasks: StateFlow<List<ToDoTask>> =
        toDoTasksRepository
            .getAllTasks()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}
