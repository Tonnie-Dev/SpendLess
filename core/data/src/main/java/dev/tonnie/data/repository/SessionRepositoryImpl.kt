package dev.tonnie.data.repository

import dev.tonnie.repository.Clock
import dev.tonnie.repository.SessionPrefs
import dev.tonnie.repository.SessionRepository
import kotlinx.coroutines.flow.first

class SessionRepositoryImpl(
    private val sessionPrefs: SessionPrefs,
    private val clock: Clock
) : SessionRepository {
    override suspend fun startSession(username: String) {
        sessionPrefs.setActiveUsername(username)
        sessionPrefs.setSessionStartTimestamp(clock.currentTimeMillis())
    }

    override suspend fun refreshSession() {
        sessionPrefs.setSessionStartTimestamp(clock.currentTimeMillis())
    }

    override suspend fun isSessionValid(): Boolean {
        val startTimestamp = sessionPrefs.sessionStartTimestamp
                .first() ?: return false

        val configuredDuration = sessionPrefs.sessionDuration.first()
        val elapsedTime = clock.currentTimeMillis() - startTimestamp

        return elapsedTime < configuredDuration
    }

    override suspend fun clearSession() {
        sessionPrefs.clearSession()
    }
}
