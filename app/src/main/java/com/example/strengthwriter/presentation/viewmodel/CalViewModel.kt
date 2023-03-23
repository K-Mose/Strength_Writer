package com.example.strengthwriter.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.PrimaryKey
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class CalViewModel @Inject constructor(
    private val setsDao: SetsDao,
    private val workoutDao: WorkoutDao
): ViewModel() {
    /*
    workout내의
    sets 리스트 생성 및 관리
     */
    val workoutName = mutableStateOf("")
    fun setWorkoutName(name: String) {
        workoutName.value = name
    }

    val exercise = mutableStateOf(Exercise.DEAD_LIFT)

    // sets list
    private val _setsList =  mutableListOf<_Sets>()

    private val _setsState =  MutableStateFlow<RequestState<MutableList<_Sets>>>(RequestState.Idle)
    val setsState: StateFlow<RequestState<List<_Sets>>> = _setsState

    fun initList() {
        _setsState.value = RequestState.Idle
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
                    date = null,
                    memo = ""
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
        repetition = this.repetition.toInt(),
        weight = this.weight.toDouble(),
        unit = this.unit,
        ratio = this.ratio.toInt(),
    )
}