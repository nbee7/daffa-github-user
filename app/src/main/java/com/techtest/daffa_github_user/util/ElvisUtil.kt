package com.techtest.daffa_github_user.util

object ElvisUtil {
    fun Boolean?.orFalse(): Boolean = this ?: false
    fun Boolean?.orTrue(): Boolean = this ?: true
    fun Long?.orZero(): Long = this ?: 0L
    fun Float?.orZero(): Float = this ?: 0f
    fun Long?.orMinus(): Long = this ?: -1L
    fun Double?.orZero(): Double = this ?: 0.0
    fun Double?.orMinus(): Double = this ?: -1.0
    fun Int?.orZero(): Int = this ?: 0
    fun Int?.orMinus(): Int = this ?: -1
}
