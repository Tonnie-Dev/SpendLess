package dev.tonnie.repository

import kotlinx.coroutines.flow.Flow

interface SessionPrefs {

    val activeUsername: Flow<String?>
    val sessionExpiryTimestamp: Flow<Long?>

    suspend fun setActiveUsername(username: String)

    suspend fun setSessionExpiryTimestamp(timestamp: Long)

    suspend fun clearSession()
}