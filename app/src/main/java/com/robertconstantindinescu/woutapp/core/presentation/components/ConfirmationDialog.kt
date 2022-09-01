package com.robertconstantindinescu.woutapp.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmationDialog(
    modifier: Modifier,
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                // horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // CircularProgressIndicator()
                // Text("Hello")
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium
                )


                Text(text = description, style = MaterialTheme.typography.bodyMedium)
                // Timber.d("message=${progressState.message}")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "NO")
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(text = "YES")
            }
        }
    )
}