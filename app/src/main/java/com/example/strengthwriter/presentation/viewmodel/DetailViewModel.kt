package com.example.strengthwriter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthwriter.data.DailyMissionDao
import com.example.strengthwriter.data.SetsDao
import com.example.strengthwriter.data.WorkoutDao
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // @Inject annotation이 필요함
class DetailViewModel @Inject constructor(
    val setsDao: SetsDao,
    val workoutDao: WorkoutDao,
    val dailyMissionDao: DailyMissionDao
) : ViewModel(){

    val title: MutableState<String> = mutableStateOf("")
    val date: MutableState<String> = mutableStateOf("")

    fun updateTitle(newTitle: String) {
        title.value = newTitle
    }

    fun updateDate(newDate: String) {
        date.value = newDate
    }

    private val _workoutList: MutableList<Workout> = mutableListOf()

    private val _workoutListState = mutableStateOf<RequestState<List<Workout>>>(RequestState.Idle)
    val workoutListState = _workoutListState

    fun addWorkout(workout: Workout) {
        _workoutListState.value = RequestState.Loading(_workoutList)
        _workoutList.add(workout)
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(50)
            _workoutListState.value = RequestState.Success(_workoutList)
        }
    }

    fun removeWorkout(index: Int) {
        _workoutListState.value = RequestState.Loading(_workoutList)
        _workoutList.removeAt(index)
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(50)
            _workoutListState.value = RequestState.Success(_workoutList)
        }
    }

    private var loadedWorkoutList = listOf<Workout>()
    private val _loadedWorkoutState = MutableStateFlow<RequestState<List<Workout>>>(RequestState.Idle)
    val loadedWorkoutState: StateFlow<RequestState<List<Workout>>> = _loadedWorkoutState

    fun loadAllWorkout() {
        Log.d("DetailViewModel::loadedWorkoutList", "start")
        _loadedWorkoutState.value = RequestState.Loading(loadedWorkoutList)
        viewModelScope.launch(Dispatchers.IO) {
            workoutDao.getAllWorkout().collect { maps ->
                maps.keys.forEach { workout ->
                    maps[workout]?.let {
                        workout.sets.addAll(
                            it.map { sets ->
                                sets.copy(id = 0)
                            }
                        )
                    }
                }
                loadedWorkoutList = maps.keys.toList()
                    .map {
                        it.copy(id = 0)
                    }
                Log.d("DetailViewModel::loadedWorkoutList", "list : $loadedWorkoutList")
                _loadedWorkoutState.value = RequestState.Success(loadedWorkoutList)
                Log.d("DetailViewModel::loadedWorkoutList", "list : $loadedWorkoutState")
            }
        }
        Log.d("DetailViewModel::loadedWorkoutList", "end")
    }

    fun addMission() {
        Log.d("DetailViewModel::loadedWorkoutList", "save")
        viewModelScope.launch {
            val missionId: Int = dailyMissionDao.insertNewDailyMission(
                DailyMission(
                    id = 0,
                    date = date.value,
                    title = title.value
                )
            ).toInt()
            _workoutList.forEach { workout ->
                val workoutId: Int = workoutDao.addNewWorkout(
                    workout.copy(missionId = missionId)
                ).toInt()
                setsDao.insertNewSetsList(
                    workout.sets.map { sets ->
                        sets.copy(workoutId = workoutId)
                    }
                )
            }
        }

    }
}