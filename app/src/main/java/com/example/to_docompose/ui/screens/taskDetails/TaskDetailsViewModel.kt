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
import com.example.to_docompose.utils.coroutines.combineState
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

    val originalTask: StateFlow<ToDoTask> =
        flow {
            if (_selectedTaskId > 0) {
                val task = toDoTasksRepository.getTask(_selectedTaskId)
                emit(task)
            }
        }
            .onEach { task ->
                setEditedTitle(task.title)
                setEditedDescription(task.description)
                setEditedPriority(task.priority)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, ToDoTask.empty(id = _selectedTaskId))

    private val _editedTitle: MutableStateFlow<String> = MutableStateFlow("")
    val editedTitle: StateFlow<String> = _editedTitle

    private val _editedDescription: MutableStateFlow<String> = MutableStateFlow("")
    val editedDescription: StateFlow<String> = _editedDescription

    private val _editedPriority: MutableStateFlow<Priority> = MutableStateFlow(Priority.MEDIUM)
    val editedPriority: StateFlow<Priority> = _editedPriority

    val editedTask: StateFlow<ToDoTask> =
        combineState(_editedTitle, _editedDescription, _editedPriority,
            viewModelScope, SharingStarted.Lazily
        ) { title, description, priority ->
            ToDoTask(
                id = _selectedTaskId,
                title = title,
                description = description,
                priority = priority,
            )
        }
    
    private val _exitConfirmationVisibility: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val exitConfirmationVisibility: StateFlow<Boolean> = _exitConfirmationVisibility

    private val _deleteConfirmationVisibility: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteConfirmationVisibility: StateFlow<Boolean> = _deleteConfirmationVisibility

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
            toDoTasksRepository.addTask(task.copy(id = 0))
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

    fun showDeleteConfirmation() {
        _deleteConfirmationVisibility.value = true
    }
    
    fun hideDeleteConfirmation() {
        _deleteConfirmationVisibility.value = false
    }

    fun showExitConfirmation() {
        _exitConfirmationVisibility.value = true
    }

    fun hideExitConfirmation() {
        _exitConfirmationVisibility.value = false
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")

        super.onCleared()
    }
}
