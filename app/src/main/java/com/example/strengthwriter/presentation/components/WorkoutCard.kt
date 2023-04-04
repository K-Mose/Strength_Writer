package com.example.strengthwriter.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.ui.theme.CARD_ELEVATION_5
import com.example.strengthwriter.ui.theme.PADDING_SMALL
import com.example.strengthwriter.ui.theme.SPACER_MEDIUM_HEIGHT

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutCard(
    workout: Workout
) {
    Surface(
//        onClick = {}
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = PADDING_SMALL),
                /*.clickable {
                    // TODO: ADD navigationg to WorkoutDetail Page & ADD ripple effect https://stackoverflow.com/questions/66703448/how-to-disable-ripple-effect-when-clicking-in-jetpack-compose
                },*/
            elevation = CARD_ELEVATION_5
        ) {
            WorkoutItem(
                workout = workout
            )
        }
        Spacer(modifier = Modifier.height(SPACER_MEDIUM_HEIGHT))
    }
}