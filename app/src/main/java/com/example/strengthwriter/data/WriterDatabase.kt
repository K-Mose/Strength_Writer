package com.example.strengthwriter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.data.model.Workout

@Database(entities = [DailyMission::class, Workout::class, Sets::class], version = 0)
abstract class WriterDatabase : RoomDatabase() {
    abstract fun dailyMissionDao(): DailyMissionDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun setsDao(): SetsDao
}