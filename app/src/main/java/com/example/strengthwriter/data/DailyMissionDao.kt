package com.example.strengthwriter.data

import androidx.room.*
import com.example.strengthwriter.data.model.*
import com.example.strengthwriter.utils.Constants.WORKOUT_TABLE
import com.example.strengthwriter.utils.Constants.SETS_TABLE
import com.example.strengthwriter.utils.Constants.MISSION_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyMissionDao {

/*
    중첩된 관계 정의로 변경
    @Query("SELECT * FROM $MISSION_TABLE ORDER BY id")
    fun getAllMissions(): Flow<List<DailyMission>>
*/
    @Query("SELECT * FROM $MISSION_TABLE")
    fun getAllMissions(): Flow<List<MissionAndWorkout>>


    @Query("SELECT * FROM $MISSION_TABLE WHERE id = :id")
    fun getMissions(id: Int): Flow<DailyMission>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewDailyMission(mission: DailyMission): Long

    @Update
    suspend fun updateDailyMission(mission: DailyMission)

    @Delete
    suspend fun deleteDailyMission(mission: DailyMission)
}