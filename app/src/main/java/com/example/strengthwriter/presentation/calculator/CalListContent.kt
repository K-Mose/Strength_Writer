package com.example.strengthwriter.presentation.calculator

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.presentation.list.WorkoutItem
import com.example.strengthwriter.presentation.viewmodel.CalViewModel
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
    LazyColumn {
        items(workoutList) {
            WorkoutItem(workout = it)
        }
    }
}