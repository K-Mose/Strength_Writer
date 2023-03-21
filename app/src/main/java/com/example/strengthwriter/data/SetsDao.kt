package com.example.strengthwriter.data

import androidx.room.*
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.utils.Constants.SETS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SetsDao {
    @Query("SELECT * FROM $SETS_TABLE WHERE workoutId = :workoutId ORDER BY id")
    fun getAllSets(workoutId: Int): Flow<List<Sets>>

    @Query("SELECT * FROM $SETS_TABLE WHERE id = :id")
    fun getSets(id: Int): Flow<Sets>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewSets(sets: Sets)

    @Update
    suspend fun updateSets(sets: Sets)

    @Delete
    suspend fun deleteSets(sets: Sets)
}