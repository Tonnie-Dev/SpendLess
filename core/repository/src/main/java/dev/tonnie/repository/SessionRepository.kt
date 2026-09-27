package dev.tonnie.repository

interface SessionRepository {

    suspend fun startSession(username: String)

    suspend fun refreshSession()

    suspend fun isSessionValid(): Boolean

    suspend fun clearSession()
}