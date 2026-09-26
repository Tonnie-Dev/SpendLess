package dev.tonnie.datastore.session

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal class SessionPrefsImpl(private val dataStore: DataStore<Preferences>) : SessionPrefs, {

    override val safeData: Flow<Preferences> = dataStore.data
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

    override val sessionExpiryTimestamp: Flow<Long?> =
        safeData.map { prefs ->
            prefs[SESSION_EXPIRY_KEY]
        }

    override suspend fun setActiveUsername(username: String) {
        dataStore.edit { prefs ->
            prefs[ACTIVE_USERNAME_KEY] = username
        }
    }

    override suspend fun setSessionExpiryTimestamp(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[SESSION_EXPIRY_KEY] = timestamp
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.remove(ACTIVE_USERNAME_KEY)
            prefs.remove(SESSION_EXPIRY_KEY)
        }
    }

    private companion object {
        val SESSION_EXPIRY_KEY = longPreferencesKey("session_expiry_timestamp")
        val ACTIVE_USERNAME_KEY = stringPreferencesKey("active_username")
    }

}

