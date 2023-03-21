package com.example.strengthwriter.utils

import java.text.DateFormat
import java.util.Date
import java.util.regex.Pattern

object Utils {
    fun Double.removeDecimal(): String {
        return this.toString().replace(".0", "")
    }
}