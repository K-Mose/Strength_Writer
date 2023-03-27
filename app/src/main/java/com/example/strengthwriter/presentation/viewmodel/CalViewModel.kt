package com.example.strengthwriter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strengthwriter.data.SetsDao
import com.example.strengthwriter.data.WorkoutDao
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.utils.Exercise
import com.example.strengthwriter.utils.RequestState
import com.example.strengthwriter.utils.Unit
import com.example.strengthwriter.utils.Utils.parseDoubleString
import com.example.strengthwriter.utils.Utils.parseNumberString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalViewModel @Inject constructor(
    private val setsDao: SetsDao,
    private val workoutDao: WorkoutDao
): ViewModel() {
    private var workoutList = listOf<Workout>()
    private val _workoutState = MutableStateFlow<RequestState<List<Workout>>>(RequestState.Idle)
    val workoutSets: StateFlow<RequestState<List<Workout>>> = _workoutState

    fun getWorkoutList() {
        Log.d("${this::class.simpleName}::", "getWorkoutList")
        _workoutState.value = RequestState.Loading(listOf())
        viewModelScope.launch(Dispatchers.IO) {
            val workouts = workoutDao.getAllWorkout()
            workouts.collect { maps ->
                Log.d("${this::class.simpleName}::", "it :: $maps")
                maps.keys.forEach { key ->
                    maps[key]?.forEach { sets ->
                        key.sets.add(sets)
                    }
                }
                workoutList = maps.keys.toList()
                _workoutState.value = RequestState.Success(workoutList)
            }
        }
    }

    /*
    workout내의
    sets 리스트 생성 및 관리
     */
    val workoutMemo = mutableStateOf("")
    fun setWorkoutMemo(memo: String) {
        workoutMemo.value = memo
    }

    val exercise = mutableStateOf(Exercise.DEAD_LIFT)

    // sets list
    private val _setsList =  mutableListOf<_Sets>()

    private val _setsState =  MutableStateFlow<RequestState<MutableList<_Sets>>>(RequestState.Idle)
    val setsState: StateFlow<RequestState<List<_Sets>>> = _setsState

    fun initList() {
        _setsState.value = RequestState.Idle
        exercise.value = Exercise.DEAD_LIFT
        workoutMemo.value = ""
    }

    fun getSetsList() {
        _setsState.value = RequestState.Loading(_setsList)
        _setsState.value = RequestState.Success(_setsList)
    }

    fun updateReps(index: Int, reps: String) {
        _setsState.value = RequestState.Loading(_setsList)
        _setsList[index] = _setsList[index].copy(repetition = reps.parseNumberString())
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(50)
            _setsState.value = RequestState.Success(_setsList)
        }
    }

    fun updateWeight(index: Int, weight: String) {
        _setsState.value = RequestState.Loading(_setsList)
        _setsList[index] = _setsList[index].copy(weight = weight.parseDoubleString())
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(50)
            _setsState.value = RequestState.Success(_setsList)
        }
    }

    fun updateRatio(index: Int, ratio: String) {
        _setsState.value = RequestState.Loading(_setsList)
        _setsList[index] = _setsList[index].copy(ratio = ratio.parseNumberString())
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(50)
            _setsState.value = RequestState.Success(_setsList)
        }
    }

    fun addSets(sets: _Sets) {
        _setsState.value = RequestState.Loading(_setsList)
        _setsList.add(sets)
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(50)
            _setsState.value = RequestState.Success(_setsList)
        }
    }

    fun removeSets(index: Int) {
        _setsState.value = RequestState.Loading(_setsList)
        _setsList.removeAt(index)
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(50)
            _setsState.value = RequestState.Success(_setsList)
        }
    }

    fun saveExercise() {
        viewModelScope.launch {
            val workoutId = workoutDao.addNewWorkout(
                Workout(
                    id =  0,
                    missionId = null,
                    name = exercise.value,
                    memo = workoutMemo.value,
                    date = null
                )
            )
            Log.d("${this::class.simpleName}::", "workoutId :: $workoutId")
            setsDao.insertNewSetsList(
                _setsList.map {
                    it.toSets(workoutId.toInt())
                }
            )
        }
    }

    fun getWorkout(): Workout = Workout(
        id = 0,
        missionId = 0,
        name = exercise.value,
        memo = workoutMemo.value,
        date = null,
        sets = _setsList.map { it.toSets(null) }.toMutableList()
    )

    fun removeWorkout(workout: Workout) {
        // remove sets, remove workout
        viewModelScope.launch(Dispatchers.IO) {
            workout.sets.forEach { sets ->
                setsDao.deleteSets(sets = sets)
            }
            workoutDao.deleteWorkout(workout = workout)
        }
    }
}

data class _Sets(
    val repetition: String,
    val weight: String,
    val unit: Unit = Unit.LBS,
    val ratio: String,
) {
    fun toSets(workoutId: Int?): Sets = Sets(
        id = 0,
        workoutId = workoutId,
        repetition = this.repetition.parseNumberString().toInt(),
        weight = this.weight.parseDoubleString().toDouble(),
        unit = this.unit,
        ratio = this.ratio.parseNumberString().toInt(),
    )
}