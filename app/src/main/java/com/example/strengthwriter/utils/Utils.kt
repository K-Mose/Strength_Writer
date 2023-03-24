package com.example.strengthwriter.utils

import com.example.strengthwriter.utils.Utils.parseNumberString
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

object Utils {
    fun Double.removeDecimal(): String {
        return this.toString().replace(".0", "")
    }

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