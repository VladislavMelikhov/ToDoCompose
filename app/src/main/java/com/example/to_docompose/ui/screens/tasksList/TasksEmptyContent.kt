package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.ToDoComposeTheme

@Composable
fun TasksEmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp),
            painter = painterResource(R.drawable.ic_inbox),
            contentDescription = stringResource(R.string.empty_list_icon),
            tint = LocalCustomColorsPalette.current.placeholderColor,
        )
        Text(
            text = stringResource(R.string.no_tasks_found),
            color = LocalCustomColorsPalette.current.placeholderColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
@Preview
private fun TasksEmptyContentPreview() {
    ToDoComposeTheme(darkTheme = false) {
        TasksEmptyContent()
    }
}
