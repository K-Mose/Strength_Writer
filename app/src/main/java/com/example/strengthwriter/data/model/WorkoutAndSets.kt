package com.example.strengthwriter.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutAndSets(
    @Embedded
    val workout: Workout,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    val sets: List<Sets>
)
