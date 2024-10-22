package com.example.to_docompose.ui.screens.taskDetails

import androidx.annotation.StringRes

sealed interface ValidationResult {

    data object Success : ValidationResult

    data class Error(
        @StringRes val messageId: Int
    ) : ValidationResult
}
