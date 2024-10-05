package com.example.to_docompose.ui.screens.taskDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
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
        flow {
            if (_selectedTaskId > 0) {
                val selectedTask = toDoTasksRepository.getTask(_selectedTaskId)
                emit(selectedTask)
            } else {
                emit(null)
            }
        }
            .onEach { task ->
                if (task != null) {
                    editedTitle.value = task.title
                    editedDescription.value = task.description
                    editedPriority.value = task.priority
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val editedTitle: MutableStateFlow<String> =
        MutableStateFlow("")

    val editedDescription: MutableStateFlow<String> =
        MutableStateFlow("")

    val editedPriority: MutableStateFlow<Priority> =
        MutableStateFlow(Priority.MEDIUM)

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}
