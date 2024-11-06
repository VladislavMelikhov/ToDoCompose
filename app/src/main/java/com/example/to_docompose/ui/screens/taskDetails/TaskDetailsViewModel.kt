package com.example.to_docompose.ui.screens.taskDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.ToDoTasksRepository
import com.example.to_docompose.ui.screens.tasksList.message.TaskMessage
import com.example.to_docompose.ui.screens.tasksList.message.TaskMessageBus
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
    private val taskMessageBus: TaskMessageBus,
) : ViewModel() {

    companion object {
        private const val TITLE_MAX_LENGTH = 20

        private const val TAG = "TaskDetailsViewModel"
    }

    init {
        Log.d(TAG, "init")
    }

    private val _selectedTaskId: Int =
        requireNotNull(savedStateHandle[TASK_DETAILS_ARG_KEY])

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

    private val _deleteConfirmationDialogState: MutableStateFlow<TaskConfirmationDialogState> =
        MutableStateFlow(TaskConfirmationDialogState.Hidden)
    val deleteConfirmationDialogState: StateFlow<TaskConfirmationDialogState> =
        _deleteConfirmationDialogState

    private val _exitConfirmationDialogState: MutableStateFlow<ConfirmationDialogState> =
        MutableStateFlow(ConfirmationDialogState.Hidden)
    val exitConfirmationDialogState: StateFlow<ConfirmationDialogState> =
        _exitConfirmationDialogState

    fun setEditedTitle(title: String) {
        _editedTitle.value = title.take(TITLE_MAX_LENGTH)
    }

    fun setEditedDescription(description: String) {
        _editedDescription.value = description
    }

    fun setEditedPriority(priority: Priority) {
        _editedPriority.value = priority
    }

    fun addTask(task: ToDoTask) {
        applicationScope.launch {
            toDoTasksRepository.addTask(task)
            taskMessageBus.sendMessage(
                TaskMessage.TaskAdded(taskTitle = task.title)
            )
        }
    }

    fun updateTask(editedTask: ToDoTask) {
        applicationScope.launch { 
            toDoTasksRepository.updateTask(editedTask)
            taskMessageBus.sendMessage(
                TaskMessage.TaskUpdated(updatedTaskTitle = editedTask.title)
            )
        }
    }

    fun deleteTask(originalTask: ToDoTask) {
        applicationScope.launch {
            toDoTasksRepository.deleteTask(originalTask.id)
            taskMessageBus.sendMessage(
                TaskMessage.TasksDeleted(tasks = listOf(originalTask))
            )
        }
    }

    fun showDeleteConfirmationDialog(originalTask: ToDoTask) {
        _deleteConfirmationDialogState.value = TaskConfirmationDialogState.Shown(originalTask)
    }

    fun hideDeleteConfirmationDialog() {
        _deleteConfirmationDialogState.value = TaskConfirmationDialogState.Hidden
    }

    fun showExitConfirmationDialog() {
        _exitConfirmationDialogState.value = ConfirmationDialogState.Shown
    }

    fun hideExitConfirmationDialog() {
        _exitConfirmationDialogState.value = ConfirmationDialogState.Hidden
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}

sealed interface TaskConfirmationDialogState {

    data class Shown(
        val task: ToDoTask,
    ) : TaskConfirmationDialogState

    data object Hidden : TaskConfirmationDialogState
}

sealed interface ConfirmationDialogState {

    data object Shown : ConfirmationDialogState
    data object Hidden : ConfirmationDialogState
}
