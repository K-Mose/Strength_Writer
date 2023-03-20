package com.example.strengthwriter.utils

enum class Unit {
    LBS,
    KG
}

fun String?.toUnit(): Unit {
    return when {
        this!!.lowercase() == "lbs" -> {
            Unit.LBS
        }
        this!!.lowercase() == "lb" -> {
            Unit.LBS
        }
        this!!.lowercase() == "found" -> {
            Unit.LBS
        }
        this!!.lowercase() == "kg" -> {
            Unit.KG
        }
        else -> Unit.KG
    }
}