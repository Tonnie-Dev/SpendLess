package dev.tonnie.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    val activeUsername: Flow<String?>

    suspend fun startSession(username: String)

    suspend fun refreshSession()

    suspend fun isSessionValid(): Boolean

    suspend fun clearSession()
}