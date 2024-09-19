package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.ComposeLocalWrapper
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette

@Composable
fun TasksAppBar() {
    DefaultTasksAppBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTasksAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.tasks),
                color = LocalCustomColorsPalette.current.topAppBarContentColor,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.topAppBarBackgroundColor
        )
    )
}

@Composable
@Preview
fun DefaultTasksAppBarPreview() {
    ComposeLocalWrapper {
        DefaultTasksAppBar()
    }
}
