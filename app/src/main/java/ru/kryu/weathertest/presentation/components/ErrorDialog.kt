package ru.kryu.weathertest.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.kryu.weathertest.R

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.error_dialog_title))
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.error_dialog_retry))
            }
        },
        modifier = modifier
    )
}