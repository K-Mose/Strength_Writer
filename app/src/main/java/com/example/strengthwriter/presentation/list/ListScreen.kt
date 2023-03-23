package com.example.strengthwriter.presentation.list

import androidx.compose.runtime.Composable
import com.example.strengthwriter.data.local.TestData
import com.example.strengthwriter.navigation.Screens
import com.example.strengthwriter.presentation.components.HomeScaffold

@Composable
fun ListScreen(
    screen: Screens,
    navigationToNewWorkout: (Int) -> Unit
) {
    val testData = TestData.missions
    HomeScaffold(
        screen = screen,
        content = {
            ListContent(missions = testData)
        },
        fabClickListener = navigationToNewWorkout
    )
}
