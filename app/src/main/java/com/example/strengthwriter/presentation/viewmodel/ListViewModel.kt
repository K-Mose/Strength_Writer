package com.example.strengthwriter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthwriter.data.DailyMissionDao
import com.example.strengthwriter.data.SetsDao
import com.example.strengthwriter.data.WorkoutDao
import com.example.strengthwriter.data.WriterDatabase
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    val database: WriterDatabase,
    val dailyMissionDao: DailyMissionDao,
    val workoutDao: WorkoutDao,
    val setsDao: SetsDao
): ViewModel() {

    private var dailyMissionList = listOf<DailyMission>()
    private val _dailyMissionState = MutableStateFlow<RequestState<List<DailyMission>>>(RequestState.Idle)
    val dailyMissionState: StateFlow<RequestState<List<DailyMission>>> = _dailyMissionState

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
                _dailyMissionState.value = RequestState.Success(dailyMissionList)
            }
        }
    }

    fun removeDailyMission(mission: DailyMission) {
        database.runInTransaction {
            viewModelScope.launch(Dispatchers.Main) {
                mission.workout.forEach { workout ->
                    workout.sets.forEach { sets ->
                        setsDao.deleteSets(sets = sets)
                    }
                    workoutDao.deleteWorkout(workout = workout)
                }
                dailyMissionDao.deleteDailyMission(mission = mission)
            }
        }
    }

}