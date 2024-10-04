package com.example.to_docompose.ui.screens.taskDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.MEDIUM_PADDING
import com.example.to_docompose.ui.theme.SMALL_PADDING
import com.example.to_docompose.ui.theme.ToDoComposeTheme

@Composable
fun TaskDetailsContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = LARGE_PADDING),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
        )
        HorizontalDivider(
            thickness = MEDIUM_PADDING,
            color = Color.Transparent,
        )
        PriorityDropDown(
            selectedPriority = priority,
            onPrioritySelected = onPrioritySelected,
        )
        HorizontalDivider(
            thickness = SMALL_PADDING,
            color = Color.Transparent,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
@Preview
private fun TaskDetailsContentPreview() {
    ToDoComposeTheme(darkTheme = true) {
        TaskDetailsContent(
            title = "Task name is usually short and should be displayed in a single line",
            onTitleChange = {},
            description = "Description of what needs to be done in this item. Can be quite large",
            onDescriptionChange = {},
            priority = Priority.HIGH,
            onPrioritySelected = {},
        )
    }
}
