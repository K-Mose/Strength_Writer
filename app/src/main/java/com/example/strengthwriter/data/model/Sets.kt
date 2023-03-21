package com.example.strengthwriter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.strengthwriter.utils.Constants.SETS_TABLE
import com.example.strengthwriter.utils.Unit


@Entity(tableName = SETS_TABLE)
data class Sets(
    @PrimaryKey
    val id: Int,
    val workoutId: Int? = null,
    val repetition: Int,
    val weight: Double,
    val unit: Unit,
)
