package com.example.strengthwriter.presentation.components

import android.util.Log
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.strengthwriter.R
import com.example.strengthwriter.ui.theme.fabBackgroundColor
import com.example.strengthwriter.ui.theme.fabIconColor


@Composable
fun Fab(
    navigationToNewWorkout: (Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            Log.d("NAVIGATION::", "navigation clicked :: $navigationToNewWorkout")
            navigationToNewWorkout(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_workout),
            tint = MaterialTheme.colors.fabIconColor
        )
    }
}