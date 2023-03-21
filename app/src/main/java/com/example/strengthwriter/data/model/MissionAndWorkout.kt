package com.example.strengthwriter.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class MissionAndWorkout(
    @Embedded
    val mission: DailyMission,
    @Relation(
        parentColumn = "id",
        entityColumn = "missionId"
    )
    val workout: Workout
)
