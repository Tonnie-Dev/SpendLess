package dev.tonnie.data.repository

import dev.tonnie.repository.Clock

class SystemClock : Clock {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}