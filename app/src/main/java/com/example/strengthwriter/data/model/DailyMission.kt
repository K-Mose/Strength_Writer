package com.example.strengthwriter.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.strengthwriter.utils.Constants.MISSION_TABLE

@Entity(tableName = MISSION_TABLE)
data class DailyMission(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val date: String,
    @Ignore
    val workout: List<Workout>?
) {
    constructor(id: Int, title: String, date: String) : this(id, title, date, null)
}