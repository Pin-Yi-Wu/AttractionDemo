package com.example.homeworkattractions.util

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date


object UiUtil {
    private val sdf = SimpleDateFormat("yyyy-MM-dd")
    val dateFormat1 = "yyyy-MM-dd"
    val dateFormat2 = "yyyy-MM-dd HH:mm:ss"
    val dateFormat3 = "yyyy/MM/dd"

    @JvmStatic
    fun formatDateString(time: String?, formatType: String?): String? {
        var formatDate: String? = ""
        if (!TextUtils.isEmpty(time)) {
            try {
                val date: Date = sdf.parse(time)
                formatDate = dateToString(date, formatType)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        return formatDate
    }

    @JvmStatic
    fun dateToString(data: Date?, formatType: String?): String? {
        return SimpleDateFormat(formatType).format(data)
    }

    @JvmStatic
    fun StringToDate(dateString: String?, formatType: String?): Date {
        var date = Date()

        if (!TextUtils.isEmpty(dateString)) {
            var format = SimpleDateFormat(formatType)
            try {
                date = format.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        return date
    }
}