package com.example.strengthwriter.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    confirmDialog: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                    Text(text = title)
            },
            text = {
                   Text(text = message)
            },
            confirmButton = {
                Button(onClick = {
                    confirmDialog()
                    closeDialog()    
                }) {
                    Text(text = "Delete")
                }
                
            },
            dismissButton = {
                Button(onClick = {
                    closeDialog()
                }) {
                    Text(text = "Cancel")
                }
            },
            onDismissRequest = { closeDialog()}
        )
    }
}