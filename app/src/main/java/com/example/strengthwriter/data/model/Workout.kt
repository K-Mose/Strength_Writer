package com.example.strengthwriter.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.strengthwriter.utils.Constants.WORKOUT_TABLE
import com.example.strengthwriter.utils.Exercise

@Entity(tableName = WORKOUT_TABLE)
data class Workout(
    @PrimaryKey
    val id: Int,
    val missionId: Int? = null,
    val name: Exercise,
    val date: String? = null,
    @Ignore
    val sets: List<Sets>,
)
