package com.example.strengthwriter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthwriter.data.DailyMissionDao
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    val dailyMissionDao: DailyMissionDao
): ViewModel() {

    private var dailyMissionList = listOf<DailyMission>()
    private val _dailyMissionState = mutableStateOf<RequestState<List<DailyMission>>>(RequestState.Idle)
    val dailyMissionState = _dailyMissionState

    fun getDailyMissionList() {
        _dailyMissionState.value = RequestState.Loading(dailyMissionList)
        viewModelScope.launch(Dispatchers.IO) {
            dailyMissionDao.getAllMissions().collect { missions ->
                val list = mutableListOf<DailyMission>()
                Log.d("${this::class.simpleName}::", "missions :: $missions")
                missions.forEach { mW ->
                    mW.workouts.forEach { wS ->
                        wS.workout.sets.addAll(wS.sets)
                        mW.mission.workout.add(wS.workout)
                    }
                    list.add(mW.mission)
                }
                dailyMissionList = list
            }
            Thread.sleep(50)
            _dailyMissionState.value = RequestState.Success(dailyMissionList)
        }
    }

}