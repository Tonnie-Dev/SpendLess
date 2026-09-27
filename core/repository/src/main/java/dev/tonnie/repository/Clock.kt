package dev.tonnie.repository

fun interface Clock{
    fun currentTimeMillis(): Long
}