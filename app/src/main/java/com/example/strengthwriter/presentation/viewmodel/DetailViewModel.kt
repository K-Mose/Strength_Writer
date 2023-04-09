package com.example.strengthwriter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
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
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.strengthwriter.utils.Units
import com.example.strengthwriter.utils.Utils.convertKg
import com.example.strengthwriter.utils.Utils.convertLbs
import kotlinx.coroutines.CoroutineExceptionHandler

@HiltViewModel // @Inject annotation이 필요함
class DetailViewModel @Inject constructor(
    private val database: WriterDatabase,
    private val setsDao: SetsDao,
    private val workoutDao: WorkoutDao,
    private val dailyMissionDao: DailyMissionDao
) : ViewModel(){

    val title: MutableState<String> = mutableStateOf("")
    val date: MutableState<String> = mutableStateOf("")
    val missionId: MutableState<Int> = mutableStateOf(0)
    val unit: MutableState<Units> = mutableStateOf(Units.LBS)
    fun updateTitle(newTitle: String) {
        title.value = newTitle
    }

    fun updateDate(newDate: String) {
        date.value = newDate
    }

    private val _workoutList: MutableList<Workout> = mutableListOf()

    private val _workoutListState = mutableStateOf<RequestState<List<Workout>>>(RequestState.Idle)
    val workoutListState = _workoutListState

    private val _mission: MutableState<DailyMission?> = mutableStateOf(null)



    fun initAll() {
        title.value = ""
        date.value = ""
        missionId.value = 0
        _workoutList.clear()
        _workoutListState.value = RequestState.Idle
        _mission.value = null
    }

    fun convertUnit() {
        _workoutListState.value = RequestState.Loading(_workoutList)
        viewModelScope.launch(Dispatchers.IO) {
            unit.value = if (unit.value == Units.LBS) Units.KG else Units.LBS
            _workoutList.replaceAll{ workout ->
                workout.sets.forEachIndexed { index, sets ->
                    if (sets.units != unit.value)
                        when(sets.units) {
                            Units.LBS -> {
                                workout.sets[index] = sets.copy(units = Units.KG, weight = sets.weight.convertKg())
                            }
                            Units.KG -> {
                                workout.sets[index] = sets.copy(units = Units.LBS, weight = sets.weight.convertLbs())
                            }
                        }
                }
                workout
            }
            _workoutListState.value = RequestState.Success(_workoutList)
        }
    }

    fun addWorkout(workout: Workout) {
        _workoutListState.value = RequestState.Loading(_workoutList)
        _workoutList.add(workout)
        viewModelScope.launch(Dispatchers.IO) {
            _workoutListState.value = RequestState.Success(_workoutList)
        }
    }

    fun removeWorkout(index: Int) {
        if (index != -1) {
            _workoutListState.value = RequestState.Loading(_workoutList)
            _workoutList.removeAt(index)
            viewModelScope.launch(Dispatchers.IO) {
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
                _loadedWorkoutState.value = RequestState.Success(loadedWorkoutList)
            }
        }
        Log.d("DetailViewModel::loadedWorkoutList", "end")
    }

    fun loadMission(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dailyMissionDao.getMission(id = id)?.collect {  mw ->
                if (mw != null) {
                    Log.d("DetailViewModel::loadMission", "mv : $mw")
                    mw.mission.also { mission ->
                        title.value = mission.title
                        date.value = mission.date
                        missionId.value = mission.id
                    }
                    // ViewModel 공유하므로 초기화 시켜줌
                    _workoutList.clear()
                    mw.workouts.onEach { ws ->
                        if (!ws.sets.isNullOrEmpty())
                            unit.value = ws.sets[0].units
                        ws.workout.sets.addAll(ws.sets)
                        _workoutList.add(ws.workout)
                    }
                    _mission.value = mw.mission.copy(
                        id = missionId.value,
                        title = title.value,
                        date = date.value,
                        workout = _workoutList
                    )
                }
                else
                    _workoutList.clear()
                _workoutListState.value = RequestState.Success(_workoutList)
            }
        }
    }

    fun addMission() {
        Log.d("DetailViewModel::loadedWorkoutList", "save")
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            database.withTransaction {
                val missionId: Int = dailyMissionDao.addNewDailyMission(
                    DailyMission(
                        id = 0,
                        date = date.value,
                        title = title.value
                    )
                ).toInt()
                _workoutList.forEach { workout ->
                    Log.d("DetailViewModel::loadedWorkoutList", "workout :: $workout")
                    val _workout = workout.copy(id = 0, missionId = missionId)
                    val workoutId: Int = workoutDao.addNewWorkout(_workout).toInt()
                    val _setsList = workout.sets.map { sets ->
                        sets.copy(id = 0, workoutId = workoutId)
                    }
                    setsDao.addNewSetsList(_setsList)
                }
            }
        }
    }

    fun updateMission() {
        /*
        새로운 데이터는 추가하고
        지울 데이터는 지움
        -> OnConflictStrategy.REPLACE, update 사용하지 않고 데이터 replace
         */
        viewModelScope.launch(Dispatchers.IO) {
            database.withTransaction {
                dailyMissionDao.addNewDailyMission(
                    mission = DailyMission(
                        id = missionId.value,
                        title = title.value,
                        date = date.value
                    )
                )
                _workoutList.forEach { workout ->
                    val workoutId = workoutDao.addNewWorkout(workout = workout.copy(missionId = missionId.value)).toInt()
                    setsDao.addNewSetsList(workout.sets.map { sets ->
                        sets.copy(workoutId = workoutId)
                    }.toMutableList())
                }

            }
        }
    }

    fun removeDailyMission() {
        val mission = _mission.value
        if (mission != null)
            viewModelScope.launch(Dispatchers.Main) {
                database.withTransaction {
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

    fun validateInputData(): Boolean {
        Log.d("Validation::", title.value)
        Log.d("Validation::", "$_workoutList")
        if (title.value.isEmpty())
            return false
        if (_workoutList.isEmpty())
            return false
        return true
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }
}