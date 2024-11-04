package dev.eric.hnreader.util

import kotlin.math.pow

fun calcTrend(votos: Int, timestamp: Long, gamma: Double = 2.0): Double {
    val hours = (System.currentTimeMillis() - timestamp) / 3600000.0
    return votos / (1 + hours).pow(gamma)
}