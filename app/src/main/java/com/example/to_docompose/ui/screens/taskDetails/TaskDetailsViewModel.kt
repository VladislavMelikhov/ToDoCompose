package com.example.to_docompose.ui.screens.taskDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import com.example.to_docompose.utils.coroutines.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val applicationScope: ApplicationScope,
    private val toDoTasksRepository: ToDoTasksRepository,
) : ViewModel() {

    companion object {
        private const val TITLE_MAX_LENGTH = 20

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
                    setEditedTitle(task.title)
                    setEditedDescription(task.description)
                    setEditedPriority(task.priority)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _editedTitle: MutableStateFlow<String> = MutableStateFlow("")
    val editedTitle: StateFlow<String> = _editedTitle

    private val _editedDescription: MutableStateFlow<String> = MutableStateFlow("")
    val editedDescription: StateFlow<String> = _editedDescription

    private val _editedPriority: MutableStateFlow<Priority> = MutableStateFlow(Priority.MEDIUM)
    val editedPriority: StateFlow<Priority> = _editedPriority

    fun setEditedTitle(title: String) {
        _editedTitle.value = title.take(TITLE_MAX_LENGTH)
    }

    fun setEditedDescription(description: String) {
        _editedDescription.value = description
    }

    fun setEditedPriority(priority: Priority) {
        _editedPriority.value = priority
    }

    fun validateFields(): ValidationResult {
        val title = _editedTitle.value
        if (title.isBlank()) {
            return ValidationResult.Error(R.string.title_is_empty)
        }

        val description = _editedDescription.value
        if (description.isBlank()) {
            return ValidationResult.Error(R.string.description_is_empty)
        }

        return ValidationResult.Success
    }

    fun addTask(task: ToDoTask) {
        applicationScope.launch {
            toDoTasksRepository.addTask(task)
        }
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}
