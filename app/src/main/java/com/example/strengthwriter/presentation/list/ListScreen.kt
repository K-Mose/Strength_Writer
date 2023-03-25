package com.example.strengthwriter.presentation.list

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    val missionState by listViewModel.dailyMissionState
    LaunchedEffect(missionState) {
        listViewModel.getDailyMissionList()
    }
    val missions: List<DailyMission> = when (missionState) {
        is RequestState.Success<List<DailyMission>> -> (missionState as RequestState.Success<List<DailyMission>>).data
        is RequestState.Loading<List<DailyMission>> -> (missionState as RequestState.Loading<List<DailyMission>>).data
        else -> listOf()
    }
    Log.d("ListScreen::", "missions :: $missions")
    HomeScaffold(
        screen = screen,
        content = {
            ListContent(missions = missions)
        },
        fabClickListener = navigationToNewWorkout
    )
}
