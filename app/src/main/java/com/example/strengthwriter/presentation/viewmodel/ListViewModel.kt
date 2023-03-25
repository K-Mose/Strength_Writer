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
                Log.d("${this::class.simpleName}::", "missions :: $missions")
//                missions.keys.forEach { missionKey ->
//
//                }
//                dailyMissionList = missions.keys.toList()
            }
        }
        _dailyMissionState.value = RequestState.Success(dailyMissionList)
    }

}