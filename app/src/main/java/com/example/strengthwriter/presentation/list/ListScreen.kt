package com.example.strengthwriter.presentation.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.strengthwriter.R
import com.example.strengthwriter.data.local.TestData
import com.example.strengthwriter.ui.theme.fabBackgroundColor
import com.example.strengthwriter.ui.theme.fabIconColor

@Composable
fun ListScreen(
    navigationToNewWorkout: (Int) -> Unit
) {
    val testData = TestData.missions

    Scaffold(
        topBar = {},
        content = {
            it
            ListContent(missions = testData)
        },
        floatingActionButton = {
            Fab(navigationToNewWorkout = navigationToNewWorkout)
        }
    )
}

@Composable
fun Fab(
    navigationToNewWorkout: (Int) -> Unit
) {
    FloatingActionButton(
        onClick = { navigationToNewWorkout(-1)},
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_workout),
            tint = MaterialTheme.colors.fabIconColor
        )
    }
}