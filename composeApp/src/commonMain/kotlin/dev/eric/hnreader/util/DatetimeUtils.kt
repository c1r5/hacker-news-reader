package dev.eric.hnreader.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.until

data class ElapsedTime(
    val time: Int,
    val unit: DateTimeUnit
)

fun elapsedTime(timestamp: Long): ElapsedTime {
    val startInstant = Instant.fromEpochSeconds(timestamp)
    val endInstant = Clock.System.now()
    val elapsedHours = startInstant.until(endInstant, DateTimeUnit.HOUR)
    val elapsedMinutes = startInstant.until(endInstant, DateTimeUnit.MINUTE)

    return elapsedHours.toInt().let {
        if (it < 0) {
            ElapsedTime(elapsedMinutes.toInt(), DateTimeUnit.MINUTE)
        } else {
            ElapsedTime(elapsedHours.toInt(), DateTimeUnit.HOUR)
        }
    }
}

