package com.example.strengthwriter.utils

import com.example.strengthwriter.utils.Utils.parseNumberString
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern
import kotlin.math.round

object Utils {
    fun Double.removeDecimal(): String {
        return this.toString().replace(".0", "")
    }

    fun Double.convertKg(): Double =
        round(this * 0.454)

    fun Double.convertLbs(): Double =
        round(this * 100 / 45.4)

    fun String.parseNumberString(): String =
        if (this.isNotEmpty())
            if(Regex("^\\d*$").matches(this))
                this
            else
                Regex("\\D").replace(this,"")
        else "0"


    fun String.parseDoubleString(): String =
        if (this.isNotEmpty())
            if(Regex("^\\d*.\\d+$").matches(this)) this
                else {
                this.split(".").joinToString(".") {
                    Regex("\\D").replace(it, "")
                }
            }
        else "0.0"


    fun Date.toFormattedString(): String {
        return SimpleDateFormat("yyyy/MM/dd").format(this)
    }


}