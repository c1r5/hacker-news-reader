package dev.eric.hnreader.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.until

fun elapsedHours(timestamp: Long): Int {
    val startInstant = Instant.fromEpochSeconds(timestamp)
    val endInstant = Clock.System.now()
    val elapsedHours = startInstant.until(endInstant, DateTimeUnit.HOUR)

    return elapsedHours.toInt()
}