package dev.tonnie.datastore.session

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.tonnie.repository.SessionPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal class SessionPrefsImpl(
    private val dataStore: DataStore<Preferences>
) : SessionPrefs {

    val safeData: Flow<Preferences> = dataStore.data
            .catch { e ->
                if (e is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            }
    override val activeUsername: Flow<String?> =
        safeData.map { prefs ->
            prefs[ACTIVE_USERNAME_KEY]
        }

    override val sessionStartTimestamp: Flow<Long?> =
        safeData.map { prefs ->
            prefs[SESSION_START_TIMESTAMP_KEY]
        }

    override val sessionDuration: Flow<Long> =
        safeData.map { prefs ->

            prefs[SESSION_DURATION_KEY] ?: DEFAULT_SESSION_DURATION_MILLIS
        }

    override suspend fun setActiveUsername(username: String) {
        dataStore.edit { prefs ->
            prefs[ACTIVE_USERNAME_KEY] = username
        }
    }

    override suspend fun setSessionStartTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[SESSION_START_TIMESTAMP_KEY] = timestamp
        }
    }

    override suspend fun setSessionDuration(duration: Long) {
        dataStore.edit { prefs ->
            prefs[SESSION_DURATION_KEY] = duration
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.remove(ACTIVE_USERNAME_KEY)
            prefs.remove(SESSION_START_TIMESTAMP_KEY)
        }
    }

    private companion object {
        val SESSION_START_TIMESTAMP_KEY = longPreferencesKey("session_start_timestamp")
        val SESSION_DURATION_KEY = longPreferencesKey("session_duration")
        val ACTIVE_USERNAME_KEY = stringPreferencesKey("active_username")

        const val DEFAULT_SESSION_DURATION_MILLIS = 5 * 60 * 1000L
    }
}

