package com.example.strengthwriter.presentation.calculator

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.presentation.components.WorkoutCard
import com.example.strengthwriter.presentation.components.WorkoutItem
import com.example.strengthwriter.presentation.viewmodel.CalViewModel
import com.example.strengthwriter.ui.theme.CARD_ELEVATION_5
import com.example.strengthwriter.ui.theme.PADDING_LARGE
import com.example.strengthwriter.ui.theme.SPACER_MEDIUM_HEIGHT
import com.example.strengthwriter.utils.RequestState

@Composable
fun CalListContent(
    calViewModel: CalViewModel = hiltViewModel()
) {
    calViewModel.getWorkoutList()
    val workoutState by calViewModel.workoutSets.collectAsState()
    val workoutList = when(workoutState) {
        is RequestState.Success<List<Workout>> -> (workoutState as RequestState.Success<List<Workout>>).data
        is RequestState.Loading<List<Workout>> -> (workoutState as RequestState.Loading<List<Workout>>).data
        else -> listOf()
    }
    Log.d("CalListContent", "$workoutList")
    LazyColumn(
        modifier = Modifier.padding(all = PADDING_LARGE)
    ) {
        items(workoutList) { workout ->
            WorkoutCard(workout = workout)
        }
    }
}