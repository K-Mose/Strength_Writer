package com.example.strengthwriter.utils

enum class Exercise(
    private val id: Int,
    private val exercise: String
) {
    SHOULDER_PRESS(1, "SHOULDER PRESS"),
    DEAD_LIFT(2, "DEAD LIFT"),
    BACK_SQUAT(3, "BACK SQUAT"),
    FRONT_SQUAT(4, "FRONT SQUAT");
    fun getName() = this.exercise

    fun getId() = this.id
}
