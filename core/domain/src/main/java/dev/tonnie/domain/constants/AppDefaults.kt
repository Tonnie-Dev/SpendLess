package dev.tonnie.domain.constants

import kotlin.time.Duration.Companion.milliseconds

object AppDefaults {

    val BANNER_DURATION_LENGTH = 2_000.milliseconds
    const val MAX_PIN_ATTEMPTS = 3
    const val PIN_LOCKOUT_DURATION_SECONDS = 30
}