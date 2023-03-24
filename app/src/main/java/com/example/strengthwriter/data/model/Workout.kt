package com.example.strengthwriter.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.strengthwriter.utils.Constants.WORKOUT_TABLE
import com.example.strengthwriter.utils.Exercise

@Entity(tableName = WORKOUT_TABLE)
data class Workout(
    // int 여야 autoGenerate 실행
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val missionId: Int? = null,
    val name: Exercise,
    val memo: String = "",
    val date: String? = null,
    @Ignore
    val sets: MutableList<Sets> = mutableListOf()
) {
    constructor(id: Int, missionId: Int?, name: Exercise, memo: String, date: String?): this(id, missionId, name, memo, date, mutableListOf())
}
