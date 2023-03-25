package com.example.strengthwriter.data.model

import androidx.room.Embedded
import androidx.room.Relation

/*
중첩된 관계
https://developer.android.com/training/data-storage/room/relationships?hl=ko#nested-relationships
 */
data class MissionAndWorkout(
    @Embedded
    val mission: DailyMission,
    @Relation(
        entity = Workout::class,
        parentColumn = "id",
        entityColumn = "missionId"
    )
    val workouts: List<WorkoutAndSets>,
)
