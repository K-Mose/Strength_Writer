package com.example.strengthwriter.data

import androidx.room.*
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.utils.Constants.SETS_TABLE
import com.example.strengthwriter.utils.Constants.WORKOUT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM $WORKOUT_TABLE w LEFT JOIN $SETS_TABLE s ON w.id = s.workoutId WHERE missionId is null ORDER BY w.id, s.id")
    fun getAllWorkout(): Flow<Map<Workout, List<Sets>>>

    @Query("SELECT * FROM $WORKOUT_TABLE WHERE missionId = :missionId ORDER BY id")
    fun getAllWorkout(missionId: Int): Flow<List<Workout>>

    @Query("SELECT * FROM $WORKOUT_TABLE WHERE id = :id")
    fun getWorkout(id: Int): Flow<Workout>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewWorkout(workout: Workout): Long // id retun type은 long

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

}