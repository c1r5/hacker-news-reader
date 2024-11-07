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
    val elapsedSeconds = startInstant.until(endInstant, DateTimeUnit.SECOND)

    return when {
        elapsedSeconds < 60 -> ElapsedTime(elapsedSeconds.toInt(), DateTimeUnit.SECOND)
        elapsedSeconds < 3600 -> ElapsedTime((elapsedSeconds / 60).toInt(), DateTimeUnit.MINUTE)
        elapsedSeconds < 86400 -> ElapsedTime((elapsedSeconds / 3600).toInt(), DateTimeUnit.HOUR)
        else -> ElapsedTime((elapsedSeconds / 86400).toInt(), DateTimeUnit.DAY)
    }
}

