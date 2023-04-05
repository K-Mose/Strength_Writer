package com.example.strengthwriter.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DisplayAlertDialog(
    title: String,
    body: @Composable () -> Unit,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    confirmText: String = "Delete",
    confirmDialog: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                    Text(text = title)
            },
            text = {
                   body()
            },
            confirmButton = {
                Button(onClick = {
                    confirmDialog()
                    closeDialog()    
                }) {
                    Text(text = confirmText)
                }
                
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    closeDialog()
                }) {
                    Text(text = "Cancel")
                }
            },
            onDismissRequest = { closeDialog()}
        )
    }
}