package com.example.strengthwriter.data

import androidx.room.*
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.utils.Constants.WORKOUT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM $WORKOUT_TABLE FROM missionId = :missionId ORDER BY id")
    fun getAllWorkout(missionId: Int): Flow<List<Workout>>

    @Query("SELECT * FROM $WORKOUT_TABLE FROM id = :id")
    fun getWorkout(id: Int): Flow<Workout>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewWorkout(workout: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

}