package com.example.to_docompose.ui.screens.tasksList.message

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskMessageBus @Inject constructor() {

    private val _message: MutableStateFlow<TaskMessage?> = MutableStateFlow(null)
    val message: StateFlow<TaskMessage?> = _message

    fun sendMessage(message: TaskMessage) {
        _message.value = message
    }

    fun onMessageHandled() {
        _message.value = null
    }
}
