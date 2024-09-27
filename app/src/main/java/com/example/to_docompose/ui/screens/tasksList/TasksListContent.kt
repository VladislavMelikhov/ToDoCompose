package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.shared.SharedViewModel
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_docompose.ui.theme.SMALL_PADDING
import com.example.to_docompose.ui.theme.TASK_ITEM_ELEVATION
import com.example.to_docompose.ui.theme.ToDoComposeTheme

@Composable
fun TasksListContent(
    sharedViewModel: SharedViewModel,
    navigateToTaskDetails: (taskId: Int) -> Unit,
) {
    val tasks: List<ToDoTask> by sharedViewModel.allTasks.collectAsState()

    if (tasks.isEmpty()) {
        TasksEmptyContent()
    } else {
        TasksList(
            tasks = tasks,
            navigateToTaskDetails = navigateToTaskDetails,
        )
    }
}

@Composable
fun TasksList(
    tasks: List<ToDoTask>,
    navigateToTaskDetails: (taskId: Int) -> Unit,
) {
    LazyColumn {
        items(
            count = tasks.size,
            key = { index ->
                tasks[index].id
            },
            itemContent = { index ->
                TasksListItem(
                    toDoTask = tasks[index],
                    navigateToTaskDetails = navigateToTaskDetails
                )
            }
        )
    }
}

@Composable
fun TasksListItem(
    toDoTask: ToDoTask,
    navigateToTaskDetails: (taskId: Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = LocalCustomColorsPalette.current.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskDetails(toDoTask.id)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = LARGE_PADDING)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f),
                    text = toDoTask.title,
                    color = LocalCustomColorsPalette.current.taskItemTextColor,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Box(
                    modifier = Modifier
                        .padding(start = LARGE_PADDING),
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE),
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color,
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SMALL_PADDING),
                text = toDoTask.description,
                color = LocalCustomColorsPalette.current.taskItemTextColor,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@Preview
fun TasksListItemPreview() {
    ToDoComposeTheme(darkTheme = false) {
        TasksListItem(
            toDoTask = ToDoTask(
                id = 123,
                title = "Sample Task Long Name Long Name",
                description = "Some description of what needs to be done in this particular task. May be very long text long text long text",
                priority = Priority.MEDIUM,
            ),
            navigateToTaskDetails = {},
        )
    }
}
