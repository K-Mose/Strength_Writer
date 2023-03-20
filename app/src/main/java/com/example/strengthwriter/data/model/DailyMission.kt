package com.example.strengthwriter.data.model

data class DailyMission(
    val id: Int,
    val date: String,
    val workouts: List<Workout>
)