package com.example.strengthwriter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthwriter.data.DailyMissionDao
import com.example.strengthwriter.data.SetsDao
import com.example.strengthwriter.data.WorkoutDao
import com.example.strengthwriter.data.WriterDatabase
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
    private val database: WriterDatabase,
    private val setsDao: SetsDao,
    private val workoutDao: WorkoutDao,
    private val dailyMissionDao: DailyMissionDao
) : ViewModel(){

    val title: MutableState<String> = mutableStateOf("")
    val date: MutableState<String> = mutableStateOf("")
    val missonId: MutableState<Int> = mutableStateOf(0)

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
        if (index != -1) {
            _workoutListState.value = RequestState.Loading(_workoutList)
            _workoutList.removeAt(index)
            viewModelScope.launch(Dispatchers.IO) {
                Thread.sleep(50)
                _workoutListState.value = RequestState.Success(_workoutList)
            }
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

    fun loadMission(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dailyMissionDao.getMission(id = id).collect {  mw ->
                mw.mission.also { mission ->
                    title.value = mission.title
                    date.value = mission.date
                    missonId.value = mission.id
                }
                // ViewModel 공유하므로 초기화 시켜줌
                _workoutList.clear()
                mw.workouts.onEach { ws ->
                    ws.workout.sets.addAll(ws.sets)
                    _workoutList.add(ws.workout)
                }
                _workoutListState.value = RequestState.Success(_workoutList)
            }
        }
    }

    fun addMission() {
        Log.d("DetailViewModel::loadedWorkoutList", "save")
        database.runInTransaction {
            viewModelScope.launch(Dispatchers.IO) {
                val missionId: Int = dailyMissionDao.insertNewDailyMission(
                    DailyMission(
                        id = 0,
                        date = date.value,
                        title = title.value
                    )
                ).toInt()
                _workoutList.forEach { workout ->
                    Log.d("DetailViewModel::loadedWorkoutList", "workout :: $workout")
                    val _workout = workout.copy(missionId = missionId)
                    val workoutId: Int = workoutDao.addNewWorkout(_workout).toInt()
                    val _setsList = workout.sets.map { sets ->
                        sets.copy(workoutId = workoutId)
                    }
                    setsDao.insertNewSetsList(_setsList)
                }
            }
        }
    }

    fun validateInputData(): Boolean {
        if (title.value.isEmpty())
            return false
        if (_workoutList.isEmpty())
            return false
        return true
    }
}