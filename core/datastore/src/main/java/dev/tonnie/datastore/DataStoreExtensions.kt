package dev.tonnie.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

const val DATASTORE_NAME = "spend_less_datastore"
val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)