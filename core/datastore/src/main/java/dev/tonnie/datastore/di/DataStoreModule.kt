package dev.tonnie.datastore.di

import dev.tonnie.datastore.dataStore
import dev.tonnie.datastore.session.SessionPrefsImpl
import dev.tonnie.repository.SessionPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {

    single { androidContext().dataStore }

    single<SessionPrefs> {
        SessionPrefsImpl(get())
    }
}

