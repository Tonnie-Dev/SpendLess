package dev.tonnie.repository

import kotlinx.coroutines.flow.Flow

interface SessionPrefs {

    val activeUsername: Flow<String?>
    val sessionStartTimestamp: Flow<Long?>
    val sessionDuration: Flow<Long>

    suspend fun setActiveUsername(username: String)

    suspend fun setSessionStartTimestamp(timestamp: Long)

    suspend fun setSessionDuration(duration: Long)

    suspend fun clearSession()
}