package com.example.strengthwriter.data.model

import com.example.strengthwriter.utils.Exercise

data class Workout(
    val id: Int,
    val name: Exercise,
    val sets: List<Sets>,
    val date: String
)
