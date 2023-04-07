package com.example.strengthwriter.utils

enum class Units {
    LBS,
    KG
}

fun String?.toUnit(): Units {
    return when {
        this!!.lowercase() == "lbs" -> {
            Units.LBS
        }
        this!!.lowercase() == "lb" -> {
            Units.LBS
        }
        this!!.lowercase() == "found" -> {
            Units.LBS
        }
        this!!.lowercase() == "kg" -> {
            Units.KG
        }
        else -> Units.KG
    }
}