package com.jvg.peopleapp.core.common

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toStringFormat(format: Int): String =
    when (format) {
        1 -> {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(this)
        }
        2 -> {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)
        }
        else -> {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)
        }
    }

fun Int.timestampToDate(format: Int): String =
    when (format) {
        1 -> {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(this.toLong() * 1000))
        }
        2 -> {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(this.toLong() * 1000))
        }
        else -> {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(this.toLong() * 1000))
        }
    }
