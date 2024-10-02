package com.example.to_docompose.ui.screens.taskDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val toDoTasksRepository: ToDoTasksRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "TaskDetailsViewModel"
    }

    init {
        Log.d(TAG, "init")
    }

    private val _selectedTaskId: Int =
        requireNotNull(savedStateHandle["taskId"])

    val selectedTask: StateFlow<ToDoTask?> =
        _selectedTaskId
            .let { taskId ->
                if (taskId > 0) {
                    toDoTasksRepository.getTask(taskId)
                } else {
                    flowOf(null)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}
