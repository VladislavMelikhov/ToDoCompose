package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_docompose.ui.theme.SMALL_PADDING
import com.example.to_docompose.ui.theme.TASK_ITEM_ELEVATION
import com.example.to_docompose.ui.theme.ToDoComposeTheme

@Composable
fun TasksListContent(
    tasks: List<ToDoTask>,
    onTaskClick: (ToDoTask) -> Unit,
    onSwipeToDelete: (ToDoTask) -> Unit,
) {
    if (tasks.isEmpty()) {
        TasksEmptyContent()
    } else {
        TasksList(
            tasks = tasks,
            onTaskClick = onTaskClick,
            onSwipeToDelete = onSwipeToDelete,
        )
    }
}

@Composable
private fun TasksList(
    tasks: List<ToDoTask>,
    onTaskClick: (ToDoTask) -> Unit,
    onSwipeToDelete: (ToDoTask) -> Unit,
) {
    LazyColumn {
        items(
            count = tasks.size,
            key = { index ->
                tasks[index].id
            },
            itemContent = { index ->
                Row(
                    modifier = Modifier
                        .animateItem(),
                ) {
                    SwipeableTasksListItem(
                        toDoTask = tasks[index],
                        onTaskClick = onTaskClick,
                        onSwipeToDelete = onSwipeToDelete,
                    )
                }
            }
        )
    }
}

@Composable
private fun SwipeableTasksListItem(
    toDoTask: ToDoTask,
    onTaskClick: (ToDoTask) -> Unit,
    onSwipeToDelete: (ToDoTask) -> Unit,
) {
    val swipeToDismissThreshold = 0.25f
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { swipeToDismissBoxValue ->
            when (swipeToDismissBoxValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipeToDelete(toDoTask)
                    true
                }
                else -> false
            }
        },
        positionalThreshold = { totalDistance -> swipeToDismissThreshold * totalDistance },
    )

    val deleteIconRotationDegrees by animateFloatAsState(
        when (swipeToDismissBoxState.targetValue) {
            SwipeToDismissBoxValue.Settled -> 0f
            else -> -45f
        },
        label = "Swipe-To-Delete icon animation",
    )
    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = {
            TasksListItemSwipeUnderlay(
                deleteIconRotationDegrees = deleteIconRotationDegrees,
            )
        },
        content = {
            TasksListItem(
                toDoTask = toDoTask,
                onTaskClick = onTaskClick,
            )
        },
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
    )
}

@Composable
private fun TasksListItem(
    toDoTask: ToDoTask,
    onTaskClick: (ToDoTask) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = LocalCustomColorsPalette.current.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            onTaskClick(toDoTask)
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
private fun TasksListItemSwipeUnderlay(
    deleteIconRotationDegrees: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.error)
            .padding(end = 24.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Icon(
            modifier = Modifier
                .rotate(deleteIconRotationDegrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = MaterialTheme.colorScheme.onError,
        )
    }
}

@Composable
@Preview
private fun TasksListItemPreview() {
    ToDoComposeTheme(darkTheme = false) {
        TasksListItem(
            toDoTask = ToDoTask(
                id = 123,
                title = "Sample Task Long Name Long Name",
                description = "Some description of what needs to be done in this particular task. May be very long text long text long text",
                priority = Priority.MEDIUM,
            ),
            onTaskClick = {},
        )
    }
}

@Composable
@Preview
private fun TasksListItemUnderlayPreview() {
    ToDoComposeTheme(darkTheme = false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
        ) {
            TasksListItemSwipeUnderlay(
                deleteIconRotationDegrees = 0f,
            )
        }
    }
}
