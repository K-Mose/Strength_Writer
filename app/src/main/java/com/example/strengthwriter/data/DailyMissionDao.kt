package com.example.strengthwriter.data

import androidx.room.*
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.utils.Constants.MISSION_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyMissionDao {
    @Query("SELECT * FROM $MISSION_TABLE ORDER BY id")
    fun getAllMissions(): Flow<List<DailyMission>>

    @Query("SELECT * FROM $MISSION_TABLE WHERE id = :id")
    fun getMissions(id: Int): Flow<DailyMission>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewDailyMission(mission: DailyMission)

    @Update
    suspend fun updateDailyMission(mission: DailyMission)

    @Delete
    suspend fun deleteDailyMission(mission: DailyMission)
}