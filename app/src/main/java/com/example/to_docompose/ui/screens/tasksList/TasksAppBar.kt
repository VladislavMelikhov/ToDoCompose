package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.ui.shared.SharedViewModel
import com.example.to_docompose.ui.theme.ComposeLocalWrapper
import com.example.to_docompose.ui.theme.ContentAlpha
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.to_docompose.ui.theme.Typography

@Composable
fun TasksAppBar(
    sharedViewModel: SharedViewModel,
) {
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> DefaultTasksAppBar(
            onSearchClick = {
                sharedViewModel.searchAppBarState.value =
                    SearchAppBarState.OPENED
            },
            onSortClick = {},
            onDeleteAllClick = {},
        )
        else -> SearchTasksAppBar(
            text = searchTextState,
            onTextChange = { newText ->
                sharedViewModel.searchTextState.value = newText
            },
            onCloseClick = {
                sharedViewModel.searchAppBarState.value =
                    SearchAppBarState.CLOSED
                sharedViewModel.searchTextState.value = ""
            },
            onSearchClick = {},
        )
    }
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
fun SearchTasksAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: (String) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        color = LocalCustomColorsPalette.current.topAppBarBackgroundColor,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.MEDIUM),
                    text = stringResource(R.string.search),
                    color = LocalCustomColorsPalette.current.topAppBarContentColor,
                )
            },
            textStyle = TextStyle(
                color = LocalCustomColorsPalette.current.topAppBarContentColor,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.DISABLED),
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = LocalCustomColorsPalette.current.topAppBarContentColor,
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClick()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon),
                        tint = LocalCustomColorsPalette.current.topAppBarContentColor,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(text)
                }
            ),
            colors = TextFieldDefaults.colors(
                cursorColor = LocalCustomColorsPalette.current.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            )
        )
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

@Composable
@Preview
fun SearchTasksAppBarPreview() {
    ComposeLocalWrapper {
        SearchTasksAppBar(
            text = "",
            onTextChange = {},
            onCloseClick = {},
            onSearchClick = {},
        )
    }
}
