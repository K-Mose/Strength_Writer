package com.example.strengthwriter.presentation.list

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.navigation.Screens
import com.example.strengthwriter.presentation.components.HomeScaffold
import com.example.strengthwriter.presentation.viewmodel.ListViewModel
import com.example.strengthwriter.utils.RequestState

@Composable
fun ListScreen(
    listViewModel: ListViewModel = hiltViewModel(),
    screen: Screens,
    navigationToNewWorkout: (Int) -> Unit,
) {
    val missionState by listViewModel.dailyMissionState.collectAsState()
    LaunchedEffect(missionState) {
        listViewModel.getDailyMissionList()
    }
    val missions: List<DailyMission> = when (missionState) {
        is RequestState.Success<List<DailyMission>> -> (missionState as RequestState.Success<List<DailyMission>>).data
        is RequestState.Loading<List<DailyMission>> -> (missionState as RequestState.Loading<List<DailyMission>>).data
        else -> listOf()
    }
    HomeScaffold(
        screen = screen,
        content = {
            ListContent(
                missions = missions,
                removeDailyMission = { mission ->
                    listViewModel.removeDailyMission(mission)
                }
            )
        },
        fabClickListener = navigationToNewWorkout
    )
}
