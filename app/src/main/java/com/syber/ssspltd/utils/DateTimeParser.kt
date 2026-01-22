package com.syber.ssspltd.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object  DateTimeParser {
    fun parseDate(value: String): LocalDate {
        return LocalDate.parse(
            value,
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
    }

    fun parseTime(value: String): LocalTime {
        return try {
            // Try 24-hour format first
            LocalTime.parse(
                value,
                DateTimeFormatter.ofPattern("HH:mm")
            )
        } catch (e: Exception) {
            // Fallback to 12-hour format
            LocalTime.parse(
                value,
                DateTimeFormatter.ofPattern("hh:mm a")
            )
        }
    }

    fun LocalTime.to12HourFormat(): String {
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        return this.format(formatter)
    }

    }