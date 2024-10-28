package com.example.to_docompose.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    onYesClick: () -> Unit,
    closeDialog: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Text(
                text = message,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Normal,
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    closeDialog()
                    onYesClick()
                }
            ) {
                Text(text = stringResource(R.string.yes))
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    closeDialog()
                }
            ) {
                Text(text = stringResource(R.string.no))
            }
        },
        onDismissRequest = {
            closeDialog()
        },
    )
}

@Composable
@Preview
private fun ConfirmationDialogPreview() {
    ConfirmationDialog(
        title = "Delete Task Confirmation Dialog",
        message = "Are you sure you want to delete this task?",
        onYesClick = {},
        closeDialog = {},
    )
}
