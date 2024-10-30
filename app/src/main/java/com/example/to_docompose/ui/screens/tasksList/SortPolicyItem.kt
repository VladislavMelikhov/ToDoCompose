package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_docompose.data.models.TasksSortPolicy
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.ToDoComposeTheme
import com.example.to_docompose.ui.theme.Typography

@Composable
fun SortPolicyItem(
    sortPolicy: TasksSortPolicy,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(sortPolicy.iconId),
            contentDescription = stringResource(sortPolicy.titleId),
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            modifier = Modifier
                .padding(start = LARGE_PADDING),
            text = stringResource(sortPolicy.titleId),
            style = Typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
@Preview
private fun SortPolicyItemPreview() {
    ToDoComposeTheme(darkTheme = false) {
        SortPolicyItem(
            sortPolicy = TasksSortPolicy.HIGHEST_PRIORITY,
        )
    }
}
