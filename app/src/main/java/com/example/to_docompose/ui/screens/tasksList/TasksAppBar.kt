package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.ui.theme.ComposeLocalWrapper
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.Typography

@Composable
fun TasksAppBar() {
    DefaultTasksAppBar(
        onSearchClick = {},
        onSortClick = {},
        onDeleteAllClick = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTasksAppBar(
    onSearchClick: () -> Unit,
    onSortClick: (Priority) -> Unit,
    onDeleteAllClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.tasks),
                color = LocalCustomColorsPalette.current.topAppBarContentColor,
            )
        },
        actions = {
            TasksAppBarActions(
                onSearchClick = onSearchClick,
                onSortClick = onSortClick,
                onDeleteAllClick = onDeleteAllClick,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.topAppBarBackgroundColor
        )
    )
}

@Composable
fun TasksAppBarActions(
    onSearchClick: () -> Unit,
    onSortClick: (Priority) -> Unit,
    onDeleteAllClick: () -> Unit,
) {
    SearchAction(
        onSearchClick = onSearchClick,
    )
    SortAction(
        onSortClick = onSortClick,
    )
    DeleteAllAction(
        onDeleteAllClick = onDeleteAllClick,
    )
}

@Composable
fun SearchAction(
    onSearchClick: () -> Unit,
) {
    IconButton(
        onClick = onSearchClick,
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_tasks),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
    }
}

@Composable
fun SortAction(
    onSortClick: (Priority) -> Unit,
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true },
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_filter_list),
            contentDescription = stringResource(R.string.sort_tasks),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    PriorityItem(Priority.HIGH)
                },
                onClick = {
                    expanded = false
                    onSortClick(Priority.HIGH)
                },
            )
            DropdownMenuItem(
                text = {
                    PriorityItem(Priority.MEDIUM)
                },
                onClick = {
                    expanded = false
                    onSortClick(Priority.MEDIUM)
                },
            )
            DropdownMenuItem(
                text = {
                    PriorityItem(Priority.LOW)
                },
                onClick = {
                    expanded = false
                    onSortClick(Priority.LOW)
                },
            )
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteAllClick: () -> Unit,
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true },
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_vertical_menu),
            contentDescription = stringResource(R.string.delete_all),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier
                            .padding(start = LARGE_PADDING),
                        text = stringResource(R.string.delete_all),
                        style = Typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteAllClick()
                },
            )
        }
    }
}

@Composable
@Preview
fun DefaultTasksAppBarPreview() {
    ComposeLocalWrapper {
        DefaultTasksAppBar(
            onSearchClick = {},
            onSortClick = {},
            onDeleteAllClick = {},
        )
    }
}
