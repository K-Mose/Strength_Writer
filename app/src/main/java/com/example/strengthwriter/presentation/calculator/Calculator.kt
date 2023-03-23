package com.example.strengthwriter.presentation.calculator

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.strengthwriter.R
import com.example.strengthwriter.navigation.Screens
import com.example.strengthwriter.presentation.components.HomeScaffold

@Composable
fun Calculator(
    screen: Screens
) {
    HomeScaffold(
        screen = screen,
        title = stringResource(R.string.sets_calculator),
        content = { Text(text = "CAL") },
        fabClickListener = screen.calDetail
    )
}